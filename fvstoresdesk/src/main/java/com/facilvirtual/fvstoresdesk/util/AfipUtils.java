package com.facilvirtual.fvstoresdesk.util;

import com.facilvirtual.fvstoresdesk.model.AfipConfig;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.rpc.ParameterMode;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.Base64;
import org.apache.axis.encoding.XMLType;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AfipUtils {
   static final Logger logger = LoggerFactory.getLogger(AfipUtils.class);

   public AfipUtils() {
   }

   public static String login(AfipConfig afipConfig) {
      String loginTicketResponse = null;

      try {
         String endpoint = afipConfig.getLoginEndpointUrl();
         String service = afipConfig.getLoginService();
         String dstDN = afipConfig.getLoginDstdn();
         String p12file = afipConfig.getLoginKeystore();
         String signer = afipConfig.getLoginKeystoreSigner();
         String p12pass = afipConfig.getLoginKeystorePassword();
         System.setProperty("http.proxyHost", afipConfig.getLoginProxyHost());
         System.setProperty("http.proxyPort", afipConfig.getLoginProxyPort());
         System.setProperty("http.proxyUser", afipConfig.getLoginProxyUser());
         System.setProperty("http.proxyPassword", afipConfig.getLoginProxyPassword());
         System.setProperty("javax.net.ssl.trustStore", afipConfig.getLoginTrustStore());
         System.setProperty("javax.net.ssl.trustStorePassword", afipConfig.getLoginTrustStorePassword());
         Long ticketGenTime = new Long(afipConfig.getLoginTicketGenTime());
         Long ticketExpTime = new Long(afipConfig.getLoginTicketExpTime());
         byte[] loginTicketRequest_xml_cms = create_cms(p12file, p12pass, signer, dstDN, service, ticketGenTime, ticketExpTime);

         try {
            loginTicketResponse = invoke_wsaa(loginTicketRequest_xml_cms, endpoint);
         } catch (Exception var12) {
            logger.error(var12.getMessage());
            logger.error(var12.toString());
         }
      } catch (Exception var13) {
         logger.error("Se produjo un error al conectar con Afip");
         logger.error(var13.getMessage());
         logger.error(var13.toString());
      }

      return loginTicketResponse;
   }

   public static String invoke_wsaa(byte[] LoginTicketRequest_xml_cms, String endpoint) throws Exception {
      String LoginTicketResponse = null;

      try {
         Service service = new Service();
         Call call = (Call)service.createCall();
         call.setTargetEndpointAddress(new URL(endpoint));
         call.setOperationName("loginCms");
         call.addParameter("request", XMLType.XSD_STRING, ParameterMode.IN);
         call.setReturnType(XMLType.XSD_STRING);
         LoginTicketResponse = (String)call.invoke(new Object[]{Base64.encode(LoginTicketRequest_xml_cms)});
      } catch (Exception var5) {
         if (var5.toString().equals("El CEE ya posee un TA valido para el acceso al WSN solicitado")) {
            logger.info("Actualmente existe un ticket de acceso válido de Afip");
         } else {
            logger.error("Error al crear ticket de acceso de Afip");
            logger.error(var5.getMessage());
            logger.error(var5.toString());
         }
      }

      return LoginTicketResponse;
   }

   public static byte[] create_cms(String p12file, String p12pass, String signer, String dstDN, String service, Long ticketGenTime, Long ticketExpTime) {
      PrivateKey pKey = null;
      X509Certificate pCertificate = null;
      byte[] asn1_cms = null;
      CertStore cstore = null;
      String SignerDN = null;

      try {
         KeyStore ks = KeyStore.getInstance("pkcs12");
         FileInputStream p12stream = new FileInputStream(p12file);
         ks.load(p12stream, p12pass.toCharArray());
         p12stream.close();
         pKey = (PrivateKey)ks.getKey(signer, p12pass.toCharArray());
         pCertificate = (X509Certificate)ks.getCertificate(signer);
         SignerDN = pCertificate.getSubjectDN().toString();
         ArrayList<X509Certificate> certList = new ArrayList();
         certList.add(pCertificate);
         if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
         }

         cstore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
      } catch (Exception var17) {
         logger.error("Error en la configuración del certificado de Afip");
         logger.error(var17.getMessage());
         logger.error(var17.toString());
      }

      String LoginTicketRequest_xml = create_LoginTicketRequest(SignerDN, dstDN, service, ticketGenTime, ticketExpTime);

      try {
         CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
         gen.addSigner(pKey, pCertificate, CMSSignedDataGenerator.DIGEST_SHA1);
         gen.addCertificatesAndCRLs(cstore);
         CMSProcessable data = new CMSProcessableByteArray(LoginTicketRequest_xml.getBytes());
         CMSSignedData signed = gen.generate(data, true, "BC");
         asn1_cms = signed.getEncoded();
      } catch (Exception var16) {
      }

      return asn1_cms;
   }

   public static String create_LoginTicketRequest(String SignerDN, String dstDN, String service, Long ticketGenTime, Long ticketExpTime) {
      Date GenTime = new Date();
      //DatatypeFactory factory = DatatypeFactory.newInstance();
     // XMLGregorianCalendar calendar = factory.newXMLGregorianCalendar();
      GregorianCalendar gentime = new GregorianCalendar();
      GregorianCalendar exptime = new GregorianCalendar();
      String UniqueId = (new Long(GenTime.getTime() / 1000L)).toString();
      gentime.setTime(new Date(GenTime.getTime() - 10980000L - ticketGenTime * 1000L));
      exptime.setTime(new Date(GenTime.getTime() + ticketExpTime * 1000L));
      // XMLGregorianCalendarImpl XMLGenTime = new XMLGregorianCalendarImpl(gentime);
      // XMLGregorianCalendarImpl XMLExpTime = new XMLGregorianCalendarImpl(exptime);
      //TODO: Revisar facturacion electronica calendaria
      String LoginTicketRequest_xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><loginTicketRequest version=\"1.0\"><header><source>" + SignerDN + "</source>" + "<destination>" + dstDN + "</destination>" + "<uniqueId>" + UniqueId + "</uniqueId>" + "<generationTime>" + ""  + "</generationTime>" + "<expirationTime>" + "XMLExpTime" + "</expirationTime>" + "</header>" + "<service>" + service + "</service>" + "</loginTicketRequest>";
      logger.debug("TRA: " + prettyFormat(LoginTicketRequest_xml, 2));
      return LoginTicketRequest_xml;
   }

   public static String prettyFormat(String input, int indent) {
      try {
         Source xmlInput = new StreamSource(new StringReader(input));
         StringWriter stringWriter = new StringWriter();
         StreamResult xmlOutput = new StreamResult(stringWriter);
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         transformerFactory.setAttribute("indent-number", indent);
         Transformer transformer = transformerFactory.newTransformer();
         transformer.setOutputProperty("indent", "yes");
         transformer.transform(xmlInput, xmlOutput);
         return xmlOutput.getWriter().toString();
      } catch (Exception var7) {
         throw new RuntimeException(var7);
      }
   }

   public static void pruebaWSFE() {
      try {
         String responseString = "";
         String outputString = "";
         String wsURL = "https://servicios1.afip.gov.ar/wsfe/service.asmx";
         URL url = new URL(wsURL);
         URLConnection connection = url.openConnection();
         HttpURLConnection httpConn = (HttpURLConnection)connection;
         ByteArrayOutputStream bout = new ByteArrayOutputStream();
         String xmlInput = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> <soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"> <soap:Body> <FEDummy xmlns=\"http://ar.gov.afip.dif.FEV1/\" /> </soap:Body> </soap:Envelope>";
         System.out.println("Xml: " + xmlInput);
         byte[] buffer = new byte[xmlInput.length()];
         buffer = xmlInput.getBytes();
         bout.write(buffer);
         byte[] b = bout.toByteArray();
         String SOAPAction = "http://ar.gov.afip.dif.facturaelectronica/FEDummy";
         httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
         httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
         httpConn.setRequestProperty("SOAPAction", SOAPAction);
         httpConn.setRequestMethod("POST");
         httpConn.setDoOutput(true);
         OutputStream out = httpConn.getOutputStream();
         out.write(b);
         out.close();
         httpConn.connect();
         System.out.println("http connection status :" + httpConn.getResponseMessage());
         InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
         BufferedReader in = new BufferedReader(isr);
         String inputLine = "";

         while((inputLine = in.readLine()) != null) {
            System.out.println(inputLine + "\n");
         }

         in.close();
      } catch (Exception var15) {
         var15.printStackTrace();
      }

   }

   private static SOAPMessage getSoapMessageFromString(String xml) throws SOAPException, IOException {
      MessageFactory factory = MessageFactory.newInstance();
      SOAPMessage message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
      MimeHeaders headers = message.getMimeHeaders();
      headers.addHeader("Content-Type", "text/xml");
      headers.addHeader("Encoding", "UTF-8");
      headers.addHeader("SOAPAction", "http://ar.gov.afip.dif.facturaelectronica/FEDummy");
      message.saveChanges();
      return message;
   }

   public static void pruebaWSFE4() {
      try {
         String wsURL = "https://wswhomo.afip.gov.ar/wsfev1/service.asmx";
         String xmlInput = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> <soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"> <soap:Body> <FEDummy xmlns=\"http://ar.gov.afip.dif.FEV1/\" /> </soap:Body> </soap:Envelope>";
         SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
         SOAPConnection connection2 = sfc.createConnection();
         SOAPMessage request = getSoapMessageFromString(xmlInput);
         request.writeTo(System.out);
         URL endpoint = new URL(wsURL);
         SOAPMessage response = connection2.call(request, endpoint);
         System.out.println(response.getContentDescription());
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }

   public static void pruebaWSFE3() {
      try {
         SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
         SOAPConnection soapConnection = soapConnectionFactory.createConnection();
         String url = "https://wswhomo.afip.gov.ar/wsfev1/service.asmx?wsdl";
         SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);
         System.out.print(soapResponse.toString());
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   private static SOAPMessage createSOAPRequest() throws Exception {
      MessageFactory messageFactory = MessageFactory.newInstance();
      SOAPMessage soapMessage = messageFactory.createMessage();
      SOAPPart soapPart = soapMessage.getSOAPPart();
      SOAPEnvelope envelope = soapPart.getEnvelope();
      SOAPBody soapBody = envelope.getBody();
      soapMessage.saveChanges();
      System.out.println("Request SOAP Message = ");
      soapMessage.writeTo(System.out);
      System.out.println();
      return soapMessage;
   }

   private static SOAPMessage createSOAPRequest2() throws Exception {
      MessageFactory messageFactory = MessageFactory.newInstance();
      SOAPMessage soapMessage = messageFactory.createMessage();
      SOAPPart soapPart = soapMessage.getSOAPPart();
      SOAPEnvelope envelope = soapPart.getEnvelope();
      envelope.addNamespaceDeclaration("sam", "http://samples.axis2.techdive.in");
      SOAPBody soapBody = envelope.getBody();
      SOAPElement soapBodyElem = soapBody.addChildElement("getStudentName", "sam");
      SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("rollNumber", "sam");
      soapBodyElem1.addTextNode("3");
      soapMessage.saveChanges();
      System.out.println("Request SOAP Message = ");
      soapMessage.writeTo(System.out);
      System.out.println();
      return soapMessage;
   }

   public static void pruebaWSFEOLD() {
      String loginTicketResponse = null;
      System.setProperty("http.proxyHost", "");
      System.setProperty("http.proxyPort", "80");
      String endpoint = "https://wswhomo.afip.gov.ar/wsfev1/service.asmx";
      String service = "wsfe";
      String dstDN = "cn=wsaahomo,o=afip,c=ar,serialNumber=CUIT 33693450239";
      String p12file = "C:\\facilvirtual\\certs\\facilvirtualTest.p12";
      String signer = "facilvirtualTest";
      String p12pass = "";
      System.setProperty("http.proxyHost", "");
      System.setProperty("http.proxyPort", "");
      System.setProperty("http.proxyUser", "");
      System.setProperty("http.proxyPassword", "");
      Long ticketTime = new Long("36000");
      byte[] dummyRequest_xml_cms = create_cms2(p12file, p12pass, signer, dstDN, service, ticketTime);

      try {
         loginTicketResponse = invoke_wsaa2(dummyRequest_xml_cms, endpoint);
      } catch (Exception var14) {
      }

      try {
         Reader tokenReader = new StringReader(loginTicketResponse);
         Document tokenDoc = (new SAXReader(false)).read(tokenReader);
         String token = tokenDoc.valueOf("/FEDummyResult/AppServer");
         String sign = tokenDoc.valueOf("/FEDummyResult/DbServer");
         System.out.println("AppServer: " + token);
         System.out.println("DbServer: " + sign);
      } catch (Exception var13) {
         System.out.println(var13);
      }

   }

   public static String invoke_wsaa2(byte[] dummyRequest_xml_cms, String endpoint) throws Exception {
      String LoginTicketResponse = null;

      try {
         Service service = new Service();
         Call call = (Call)service.createCall();
         call.setTargetEndpointAddress(new URL(endpoint));
         call.setOperationName("FEDummy");
         call.addParameter("request", XMLType.XSD_STRING, ParameterMode.IN);
         call.setReturnType(XMLType.XSD_STRING);
         LoginTicketResponse = (String)call.invoke(new Object[]{Base64.encode(dummyRequest_xml_cms)});
      } catch (Exception var5) {
         logger.error(var5.getMessage());
         logger.error(var5.toString());
      }

      return LoginTicketResponse;
   }

   public static byte[] create_cms2(String p12file, String p12pass, String signer, String dstDN, String service, Long TicketTime) {
      PrivateKey pKey = null;
      X509Certificate pCertificate = null;
      byte[] asn1_cms = null;
      CertStore cstore = null;
      String SignerDN = null;

      try {
         KeyStore ks = KeyStore.getInstance("pkcs12");
         FileInputStream p12stream = new FileInputStream(p12file);
         ks.load(p12stream, p12pass.toCharArray());
         p12stream.close();
         pKey = (PrivateKey)ks.getKey(signer, p12pass.toCharArray());
         pCertificate = (X509Certificate)ks.getCertificate(signer);
         SignerDN = pCertificate.getSubjectDN().toString();
         ArrayList<X509Certificate> certList = new ArrayList();
         certList.add(pCertificate);
         if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
         }

         cstore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
      } catch (Exception var16) {
         logger.error(var16.getMessage());
         logger.error(var16.toString());
      }

      String LoginTicketRequest_xml = create_dummyRequest(SignerDN, dstDN, service, TicketTime);

      try {
         CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
         gen.addSigner(pKey, pCertificate, CMSSignedDataGenerator.DIGEST_SHA1);
         gen.addCertificatesAndCRLs(cstore);
         CMSProcessable data = new CMSProcessableByteArray(LoginTicketRequest_xml.getBytes());
         CMSSignedData signed = gen.generate(data, true, "BC");
         asn1_cms = signed.getEncoded();
      } catch (Exception var15) {
         logger.error(var15.getMessage());
         logger.error(var15.toString());
      }

      return asn1_cms;
   }

   public static String create_dummyRequest(String SignerDN, String dstDN, String service, Long TicketTime) {
      String requestXml = null;

      try {
         requestXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><FEDummy version=\"1.0\"></FEDummy>";
      } catch (Exception var6) {
         logger.error(var6.getMessage());
         logger.error(var6.toString());
      }

      return requestXml;
   }

   public static int createAfipControlDigit(String barCode) {
      String input = barCode;
    

      int controlDigit;
      try {
         int etapa1 = 0;

         int etapa2;
         for(etapa2 = 0; etapa2 < input.length(); ++etapa2) {
            if (etapa2 % 2 == 0) {
               etapa1 += Integer.valueOf(input.substring(etapa2, etapa2 + 1));
            }
         }

         etapa2 = etapa1 * 3;
         int etapa3 = 0;

         int etapa4;
         for(etapa4 = 0; etapa4 < input.length(); ++etapa4) {
            if ((etapa4 + 1) % 2 == 0) {
               etapa3 += Integer.valueOf(input.substring(etapa4, etapa4 + 1));
            }
         }

         etapa4 = etapa2 + etapa3;
         int modulo = 10;

         for(controlDigit = 0; (etapa4 + controlDigit) % modulo != 0; ++controlDigit) {
         }
      } catch (Exception var8) {
         logger.error(barCode);
         controlDigit = 0;
         logger.error(var8.getMessage());
         logger.error(var8.toString());
      }

      return controlDigit;
   }

   public static int getAfipCbteTipoByName(String cbteTipoName) {
      // int afipCbteTipo = false;
      // byte afipCbteTipo;
      int afipCbteTipo;
      //int afipCbteTipo;
      switch (cbteTipoName) {
         case "Nota de Crédito A":
            afipCbteTipo = 3;
            return afipCbteTipo;
         case "Nota de Crédito B":
            afipCbteTipo = 8;
            return afipCbteTipo;
         case "Nota de Crédito C":
            afipCbteTipo = 13;
            return afipCbteTipo;
         case "Factura A":
            afipCbteTipo = 1;
            return afipCbteTipo;
         case "Factura B":
            afipCbteTipo = 6;
            return afipCbteTipo;
         case "Factura C":
            afipCbteTipo = 11;
            return afipCbteTipo;
      }

      afipCbteTipo = 0;
      return afipCbteTipo;
   }
}
