package com.facilvirtual.fvstoresdesk.service;

import com.facilvirtual.fvstoresdesk.model.AfipConfig;
import com.facilvirtual.fvstoresdesk.model.AfipProp;
import com.facilvirtual.fvstoresdesk.model.AppConfig;
import com.facilvirtual.fvstoresdesk.model.NotaDeCredito;
import com.facilvirtual.fvstoresdesk.model.Order;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Service
@Transactional
public class AfipService {
   protected static Logger logger = LoggerFactory.getLogger(AfipService.class);
   @Autowired
   private AfipConfig afipConfig;

   public AfipService() {
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

   public static String getSOAPMessageAsString(SOAPMessage soapMessage) {
      try {
         TransformerFactory tff = TransformerFactory.newInstance();
         Transformer tf = tff.newTransformer();
         tf.setOutputProperty("indent", "yes");
         tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
         Source sc = soapMessage.getSOAPPart().getContent();
         ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
         StreamResult result = new StreamResult(streamOut);
         tf.transform(sc, result);
         String strMessage = streamOut.toString();
         return strMessage;
      } catch (Exception var7) {
         System.out.println("Exception in getSOAPMessageAsString " + var7.getMessage());
         return null;
      }
   }

   public void listTiposCbte() {
      try {
         AppConfig appConfig = this.getAppConfigService().getAppConfig();
         AfipProp afipProp = this.getAppConfigService().getAfipProp();
         String endpointUrl = "https://wswhomo.afip.gov.ar/wsfev1/service.asmx";
         String soapAction = "http://ar.gov.afip.dif.FEV1/FEParamGetTiposCbte";
         String xmlInput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ar=\"http://ar.gov.afip.dif.FEV1/\"><soapenv:Body><ar:FEParamGetTiposCbte><ar:Auth><ar:Token>" + afipProp.getAfipToken() + "</ar:Token>" + "<ar:Sign>" + afipProp.getAfipSign() + "</ar:Sign>" + "<ar:Cuit>" + appConfig.getCompanyCuit() + "</ar:Cuit>" + "</ar:Auth>" + "</ar:FEParamGetTiposCbte>" + "</soapenv:Body>" + "</soapenv:Envelope>";
         String xmlResponse = this.invokeWSFE(endpointUrl, soapAction, xmlInput);
         logger.debug("Rta: " + prettyFormat(xmlResponse, 2));
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }

   public AfipConfig getAfipConfig() {
      return this.afipConfig;
   }

   public void setAfipConfig(AfipConfig afipConfig) {
      this.afipConfig = afipConfig;
   }

   public Long getCompUltimoAutorizadoAfip(int afipCbteTipo) {
      Long ultimo = -1L;

      try {
         AppConfig appConfig = this.getAppConfigService().getAppConfig();
         AfipProp afipProp = this.getAppConfigService().getAfipProp();
         String endpointUrl = this.getAfipConfig().getEndpointUrl();
         String soapAction = this.getAfipConfig().getSOAPActionFECompUltimoAutorizado();
         String xmlInput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"                   xmlns:ar=\"" + this.getAfipConfig().getXmlnsAr() + "\">" + "<soapenv:Body>" + "<ar:FECompUltimoAutorizado>" + "<ar:Auth>" + "<ar:Token>" + afipProp.getAfipToken() + "</ar:Token>" + "<ar:Sign>" + afipProp.getAfipSign() + "</ar:Sign>" + "<ar:Cuit>" + appConfig.getCompanyCuit() + "</ar:Cuit>" + "</ar:Auth>" + "<ar:PtoVta>" + appConfig.getAfipPtoVta() + "</ar:PtoVta>" + "<ar:CbteTipo>" + afipCbteTipo + "</ar:CbteTipo>" + "</ar:FECompUltimoAutorizado>" + "</soapenv:Body>" + "</soapenv:Envelope>";
         String xmlResponse = this.invokeWSFE(endpointUrl, soapAction, xmlInput);

         try {
            MessageFactory mf = MessageFactory.newInstance();
            MimeHeaders header = new MimeHeaders();
            header.addHeader("Content-Type", "text/xml");
            InputStream fis = new ByteArrayInputStream(xmlResponse.getBytes());
            SOAPMessage soapMessage = mf.createMessage(header, fis);
            SOAPBody soapBody = soapMessage.getSOAPBody();
            NodeList nodes = soapBody.getElementsByTagName("Err");
            Node node = nodes.item(0);
            if (node != null) {
               ultimo = -1L;
            } else {
               nodes = soapBody.getElementsByTagName("CbteNro");
               String nro = null;
               node = nodes.item(0);
               nro = node != null ? node.getTextContent() : "0";
               ultimo = new Long(nro);
            }
         } catch (Exception var17) {
         }
      } catch (Exception var18) {
      }

      logger.debug("Último comprobante autorizado: " + ultimo);
      return ultimo;
   }

   public String invokeWSFE(String endpointUrl, String soapAction, String xmlInput) {
      String xmlResponse = null;

      try {
         URL url = new URL(endpointUrl);
         URLConnection connection = url.openConnection();
         HttpURLConnection httpConn = (HttpURLConnection)connection;
         ByteArrayOutputStream bout = new ByteArrayOutputStream();
         logger.debug("Endpoint: " + endpointUrl);
         logger.debug("SOAP Action: " + soapAction);
         logger.debug("Xml input: " + prettyFormat(xmlInput, 2));
         byte[] buffer = new byte[xmlInput.length()];
         buffer = xmlInput.getBytes();
         bout.write(buffer);
         byte[] b = bout.toByteArray();
         httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
         httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
         httpConn.setRequestProperty("SOAPAction", soapAction);
         httpConn.setRequestMethod("POST");
         httpConn.setDoOutput(true);
         OutputStream out = httpConn.getOutputStream();
         out.write(b);
         out.close();
         httpConn.connect();
         logger.debug("http connection status :" + httpConn.getResponseMessage());
         InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
         BufferedReader in = new BufferedReader(isr);
         xmlResponse = "";

         for(String inputLine = ""; (inputLine = in.readLine()) != null; xmlResponse = xmlResponse + inputLine + "\n") {
         }

         logger.debug("Xml response: " + prettyFormat(xmlResponse, 2));
         in.close();
      } catch (Exception var15) {
      }

      return xmlResponse;
   }

   public void dummy() {
      try {
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
      } catch (Exception var14) {
         var14.printStackTrace();
      }

   }

   public void dummyDev() {
      try {
         String wsURL = "https://wswhomo.afip.gov.ar/wsfev1/service.asmx";
         URL url = new URL(wsURL);
         URLConnection connection = url.openConnection();
         HttpURLConnection httpConn = (HttpURLConnection)connection;
         ByteArrayOutputStream bout = new ByteArrayOutputStream();
         String xmlInput = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"                    xmlns:ar=\"http://ar.gov.afip.dif.FEV1/\"> <soapenv:Body> <ar:FEDummy /> </soapenv:Body> </soapenv:Envelope>";
         System.out.println("Xml: " + xmlInput);
         byte[] buffer = new byte[xmlInput.length()];
         buffer = xmlInput.getBytes();
         bout.write(buffer);
         byte[] b = bout.toByteArray();
         String SOAPAction = " http://ar.gov.afip.dif.FEV1/FEDummy";
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
      } catch (Exception var14) {
      }

   }

   public String generateCaeForOrder(Order order) {
      logger.info("Creando factura electrónica para venta con id: " + order.getId());
      String errorMsg = "";

      try {
         String wsURL = this.getAfipConfig().getEndpointUrl();
         URL url = new URL(wsURL);
         URLConnection connection = url.openConnection();
         HttpURLConnection httpConn = (HttpURLConnection)connection;
         ByteArrayOutputStream bout = new ByteArrayOutputStream();
         String xmlInput = this.createCaeXmlEnvelopeForOrder(order);
         logger.debug("Xml input: " + prettyFormat(xmlInput, 2));
         byte[] buffer = new byte[xmlInput.length()];
         buffer = xmlInput.getBytes();
         bout.write(buffer);
         byte[] b = bout.toByteArray();
         String SOAPAction = this.getAfipConfig().getSOAPActionFECAESolicitar();
         logger.debug("SOAPAction: " + SOAPAction);
         httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
         httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
         httpConn.setRequestProperty("SOAPAction", SOAPAction);
         httpConn.setRequestMethod("POST");
         httpConn.setDoOutput(true);
         OutputStream out = httpConn.getOutputStream();
         out.write(b);
         out.close();
         httpConn.connect();
         logger.debug("http connection status: " + httpConn.getResponseMessage());
         InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
         BufferedReader in = new BufferedReader(isr);
         String inputLine = "";

         String xmlResponse;
         for(xmlResponse = ""; (inputLine = in.readLine()) != null; xmlResponse = xmlResponse + inputLine + "\n") {
         }

         logger.debug("Xml response: " + prettyFormat(xmlResponse, 2));
         in.close();
         if (httpConn.getResponseCode() != 200) {
            errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde.";
         } else {
            try {
               MessageFactory mf = MessageFactory.newInstance();
               MimeHeaders header = new MimeHeaders();
               header.addHeader("Content-Type", "text/xml");
               InputStream fis = new ByteArrayInputStream(xmlResponse.getBytes());
               SOAPMessage soapMessage = mf.createMessage(header, fis);
               SOAPBody soapBody = soapMessage.getSOAPBody();
               if (soapBody.getElementsByTagName("Resultado").item(0).getTextContent().equals("R")) {
                  try {
                     NodeList nodes = soapBody.getElementsByTagName("Err");
                     Node node = nodes.item(0);
                     if (node != null) {
                        if ("10015".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "El documento ingresado no es válido";
                        } else if ("10016".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "La fecha del comprobante no es válida";
                        } else if ("10013".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "Para comprobantes clase A el tipo de documento debe ser CUIT";
                        } else if ("500".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde. (Cód. Error 500)";
                        } else if ("501".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde. (Cód. Error 501)";
                        } else if ("502".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde. (Cód. Error 502)";
                        } else if ("10000".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "La CUIT informada no está autorizada a emitir comprobantes clase A";
                        } else if ("10005".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "El Punto de Venta no está autorizado a emitir comprobantes electrónicos";
                        } else if (!"10061".equals(node.getChildNodes().item(0).getTextContent()) && !"10070".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = node != null ? node.getChildNodes().item(0).getTextContent() : "";
                           errorMsg = errorMsg + " - ";
                           errorMsg = errorMsg + (node != null ? node.getChildNodes().item(1).getTextContent() : "");
                           errorMsg = errorMsg + " ";
                        } else {
                           errorMsg = "No se puede generar el CAE porque la venta contiene artículos con IVA 0%";
                        }
                     }

                     NodeList nodes2 = soapBody.getElementsByTagName("Obs");
                     Node node2 = nodes2.item(0);
                     if (node2 != null) {
                        if ("10015".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "El documento ingresado no es válido";
                        } else if ("10016".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "La fecha del comprobante no es válida";
                        } else if ("10013".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "Para comprobantes clase A el tipo de documento debe ser CUIT";
                        } else if ("500".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde. (Cód. Error 500)";
                        } else if ("501".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde. (Cód. Error 501)";
                        } else if ("502".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde. (Cód. Error 502)";
                        } else if ("10000".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "La CUIT informada no está autorizada a emitir comprobantes clase A";
                        } else if ("10005".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "El Punto de Venta no está autorizado a emitir comprobantes electrónicos";
                        } else if (!"10061".equals(node2.getChildNodes().item(0).getTextContent()) && !"10070".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = node2 != null ? node2.getChildNodes().item(0).getTextContent() : "";
                           errorMsg = errorMsg + " - ";
                           errorMsg = errorMsg + (node2 != null ? node2.getChildNodes().item(1).getTextContent() : "");
                           errorMsg = errorMsg + " ";
                        } else {
                           errorMsg = "No se puede generar el CAE porque la venta contiene artículos con IVA 0%";
                        }
                     }
                  } catch (Exception var27) {
                     errorMsg = "Se produjo un error desconocido.";
                     logger.error(var27.getMessage());
                     logger.error(var27.toString());
                  }

                  logger.info("Xml input: " + prettyFormat(xmlInput, 2));
                  logger.info("Xml response: " + prettyFormat(xmlResponse, 2));
               }

               if (soapBody.getElementsByTagName("Resultado").item(0).getTextContent().equals("A") || soapBody.getElementsByTagName("Resultado").item(0).getTextContent().equals("P")) {
                  try {
                     String afipCae = soapBody.getElementsByTagName("CAE").item(0).getTextContent();
                     String afipCaeFchVto = soapBody.getElementsByTagName("CAEFchVto").item(0).getTextContent();
                     order.setAfipCae(afipCae);
                     order.setAfipCaeFchVto(afipCaeFchVto);
                  } catch (Exception var26) {
                     logger.error(var26.getMessage());
                     logger.error(var26.toString());
                  }
               }
            } catch (Exception var28) {
               logger.error(var28.getMessage());
               logger.error(var28.toString());
            }
         }
      } catch (Exception var29) {
         logger.error(var29.getMessage());
         logger.error(var29.toString());
      }

      return errorMsg;
   }

   public OrderService getOrderService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (OrderService)context.getBean("orderService");
   }

   private String createCaeXmlEnvelopeForOrder(Order order) {
      Long compNro = this.getCompUltimoAutorizadoAfip(order.getAfipCbteTipo()) + 1L;
      order.setAfipCbteDesde(compNro);
      order.setAfipCbteHasta(compNro);
      AppConfig appConfig = this.getAppConfigService().getAppConfig();
      AfipProp afipProp = this.getAppConfigService().getAfipProp();
      String xml = "";
      if (order.getAfipCbteTipo() == 11) {
         xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"                   xmlns:ar=\"" + this.getAfipConfig().getXmlnsAr() + "\">" + "<soapenv:Body>" + "<ar:FECAESolicitar>" + "<ar:Auth>" + "<ar:Token>" + afipProp.getAfipToken() + "</ar:Token>" + "<ar:Sign>" + afipProp.getAfipSign() + "</ar:Sign>" + "<ar:Cuit>" + appConfig.getCompanyCuit() + "</ar:Cuit>" + "</ar:Auth>" + "<ar:FeCAEReq>" + "<ar:FeCabReq>" + "<ar:CantReg>1</ar:CantReg>" + "<ar:PtoVta>" + order.getAfipPtoVta() + "</ar:PtoVta>" + "<ar:CbteTipo>" + order.getAfipCbteTipo() + "</ar:CbteTipo>" + "</ar:FeCabReq>" + "<ar:FeDetReq>" + "<ar:FECAEDetRequest>" + "<ar:Concepto>" + order.getAfipConcepto() + "</ar:Concepto>" + "<ar:DocTipo>" + order.getAfipDocTipo() + "</ar:DocTipo>" + "<ar:DocNro>" + order.getAfipDocNro() + "</ar:DocNro>" + "<ar:CbteDesde>" + order.getAfipCbteDesde() + "</ar:CbteDesde>" + "<ar:CbteHasta>" + order.getAfipCbteHasta() + "</ar:CbteHasta>" + "<ar:CbteFch>" + order.getAfipCbteFch() + "</ar:CbteFch>" + "<ar:ImpTotal>" + order.getTotalToAfip() + "</ar:ImpTotal>" + "<ar:ImpTotalConc>0</ar:ImpTotalConc>" + "<ar:ImpNeto>" + order.getTotalToAfip() + "</ar:ImpNeto>" + "<ar:ImpOpEx>0</ar:ImpOpEx>" + "<ar:ImpTrib>0</ar:ImpTrib>" + "<ar:ImpIVA>0</ar:ImpIVA>" + "<ar:FchServDesde>" + order.getAfipFchServDesde() + "</ar:FchServDesde>" + "<ar:FchServHasta>" + order.getAfipFchServHasta() + "</ar:FchServHasta>" + "<ar:FchVtoPago>" + order.getAfipFchVtoPago() + "</ar:FchVtoPago>" + "<ar:MonId>PES</ar:MonId>" + "<ar:MonCotiz>1</ar:MonCotiz>" + "</ar:FECAEDetRequest>" + "</ar:FeDetReq>" + "</ar:FeCAEReq>" + "</ar:FECAESolicitar>" + "</soapenv:Body>" + "</soapenv:Envelope>";
      } else if (order.getAfipCbteTipo() == 6 || order.getAfipCbteTipo() == 1) {
         xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"                   xmlns:ar=\"" + this.getAfipConfig().getXmlnsAr() + "\">" + "<soapenv:Body>" + "<ar:FECAESolicitar>" + "<ar:Auth>" + "<ar:Token>" + afipProp.getAfipToken() + "</ar:Token>" + "<ar:Sign>" + afipProp.getAfipSign() + "</ar:Sign>" + "<ar:Cuit>" + appConfig.getCompanyCuit() + "</ar:Cuit>" + "</ar:Auth>" + "<ar:FeCAEReq>" + "<ar:FeCabReq>" + "<ar:CantReg>1</ar:CantReg>" + "<ar:PtoVta>" + appConfig.getAfipPtoVta() + "</ar:PtoVta>" + "<ar:CbteTipo>" + order.getAfipCbteTipo() + "</ar:CbteTipo>" + "</ar:FeCabReq>" + "<ar:FeDetReq>" + "<ar:FECAEDetRequest>" + "<ar:Concepto>" + order.getAfipConcepto() + "</ar:Concepto>" + "<ar:DocTipo>" + order.getAfipDocTipo() + "</ar:DocTipo>" + "<ar:DocNro>" + order.getAfipDocNro() + "</ar:DocNro>" + "<ar:CbteDesde>" + order.getAfipCbteDesde() + "</ar:CbteDesde>" + "<ar:CbteHasta>" + order.getAfipCbteHasta() + "</ar:CbteHasta>" + "<ar:CbteFch>" + order.getAfipCbteFch() + "</ar:CbteFch>" + "<ar:ImpTotal>" + order.getTotalToAfip() + "</ar:ImpTotal>" + "<ar:ImpTotalConc>" + order.getImpTotalConcToAfip() + "</ar:ImpTotalConc>" + "<ar:ImpNeto>" + order.getImpNetoToAfip() + "</ar:ImpNeto>" + "<ar:ImpOpEx>0</ar:ImpOpEx>" + "<ar:ImpTrib>0</ar:ImpTrib>" + "<ar:ImpIVA>" + order.getImpIVAToAfip() + "</ar:ImpIVA>" + "<ar:FchServDesde>" + order.getAfipFchServDesde() + "</ar:FchServDesde>" + "<ar:FchServHasta>" + order.getAfipFchServHasta() + "</ar:FchServHasta>" + "<ar:FchVtoPago>" + order.getAfipFchVtoPago() + "</ar:FchVtoPago>" + "<ar:MonId>PES</ar:MonId>" + "<ar:MonCotiz>1</ar:MonCotiz>" + "<ar:Iva>";
         if (order.getImporteIVA105() > 0.0) {
            xml = xml + "<ar:AlicIva><ar:Id>4</ar:Id><ar:BaseImp>" + order.getBaseImpIVA105ToAfip() + "</ar:BaseImp>" + "<ar:Importe>" + order.getImporteIVA105ToAfip() + "</ar:Importe>" + "</ar:AlicIva>";
         }

         if (order.getImporteIVA21() > 0.0) {
            xml = xml + "<ar:AlicIva><ar:Id>5</ar:Id><ar:BaseImp>" + order.getBaseImpIVA21ToAfip() + "</ar:BaseImp>" + "<ar:Importe>" + order.getImporteIVA21ToAfip() + "</ar:Importe>" + "</ar:AlicIva>";
         }

         xml = xml + "</ar:Iva></ar:FECAEDetRequest></ar:FeDetReq></ar:FeCAEReq></ar:FECAESolicitar></soapenv:Body></soapenv:Envelope>";
      }

      return xml;
   }

   private String createCaeXmlEnvelopeExample() {
      AppConfig appConfig = this.getAppConfigService().getAppConfig();
      AfipProp afipProp = this.getAppConfigService().getAfipProp();
      String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ar=\"http://ar.gov.afip.dif.FEV1/\"><soapenv:Body><ar:FECAESolicitar><ar:Auth><ar:Token>" + afipProp.getAfipToken() + "</ar:Token>" + "<ar:Sign>" + afipProp.getAfipSign() + "</ar:Sign>" + "<ar:Cuit>20284511353</ar:Cuit>" + "</ar:Auth>" + "<ar:FeCAEReq>" + "<ar:FeCabReq>" + "<ar:CantReg>1</ar:CantReg>" + "<ar:PtoVta>1</ar:PtoVta>" + "<ar:CbteTipo>1</ar:CbteTipo>" + "</ar:FeCabReq>" + "<ar:FeDetReq>" + "<ar:FECAEDetRequest>" + "<ar:Concepto>1</ar:Concepto>" + "<ar:DocTipo>80</ar:DocTipo>" + "<ar:DocNro>20111111112</ar:DocNro>" + "<ar:CbteDesde>1</ar:CbteDesde>" + "<ar:CbteHasta>1</ar:CbteHasta>" + "<ar:CbteFch>20180903</ar:CbteFch>" + "<ar:ImpTotal>176.25</ar:ImpTotal>" + "<ar:ImpTotalConc>0</ar:ImpTotalConc>" + "<ar:ImpNeto>150</ar:ImpNeto>" + "<ar:ImpOpEx>0</ar:ImpOpEx>" + "<ar:ImpTrib>0</ar:ImpTrib>" + "<ar:ImpIVA>26.25</ar:ImpIVA>" + "<ar:FchServDesde></ar:FchServDesde>" + "<ar:FchServHasta></ar:FchServHasta>" + "<ar:FchVtoPago></ar:FchVtoPago>" + "<ar:MonId>PES</ar:MonId>" + "<ar:MonCotiz>1</ar:MonCotiz>" + "<ar:Iva>" + "<ar:AlicIva>" + "<ar:Id>5</ar:Id>" + "<ar:BaseImp>100</ar:BaseImp>" + "<ar:Importe>21</ar:Importe>" + "</ar:AlicIva>" + "<ar:AlicIva>" + "<ar:Id>4</ar:Id>" + "<ar:BaseImp>50</ar:BaseImp>" + "<ar:Importe>5.25</ar:Importe>" + "</ar:AlicIva>" + "</ar:Iva>" + "</ar:FECAEDetRequest>" + "</ar:FeDetReq>" + "</ar:FeCAEReq>" + "</ar:FECAESolicitar>" + "</soapenv:Body>" + "</soapenv:Envelope>";
      return xml;
   }

   private String createCaeXmlEnvelope2() {
      AppConfig appConfig = this.getAppConfigService().getAppConfig();
      AfipProp afipProp = this.getAppConfigService().getAfipProp();
      String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><FECAESolicitar xmlns=\"http://ar.gov.afip.dif.FEV1/\"><Auth><Token>" + afipProp.getAfipToken() + "</Token>" + "<Sign>" + afipProp.getAfipSign() + "</Sign>" + "<Cuit>20284511353</Cuit>" + "</Auth>" + "<FeCAEReq>" + "<FeCabReq />" + "<FeDetReq>" + "<FECAEDetRequest />" + "<FECAEDetRequest />" + "</FeDetReq>" + "</FeCAEReq>" + "</FECAESolicitar>" + "</soap:Body>" + "</soap:Envelope>";
      return xml;
   }

   public AppConfigService getAppConfigService() {
      ApplicationContext context = ApplicationContextProvider.getApplicationContext();
      return (AppConfigService)context.getBean("appConfigService");
   }

   public String generateCaeForNotaDeCredito(NotaDeCredito notaDeCredito) {
      logger.info("Creando comprobante electrónico para nota de crédito con id: " + notaDeCredito.getId());
      String errorMsg = "";

      try {
         String wsURL = this.getAfipConfig().getEndpointUrl();
         URL url = new URL(wsURL);
         URLConnection connection = url.openConnection();
         HttpURLConnection httpConn = (HttpURLConnection)connection;
         ByteArrayOutputStream bout = new ByteArrayOutputStream();
         String xmlInput = this.createCaeXmlEnvelopeForNotaDeCredito(notaDeCredito);
         logger.debug("Xml input: " + prettyFormat(xmlInput, 2));
         byte[] buffer = new byte[xmlInput.length()];
         buffer = xmlInput.getBytes();
         bout.write(buffer);
         byte[] b = bout.toByteArray();
         String SOAPAction = this.getAfipConfig().getSOAPActionFECAESolicitar();
         httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
         httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
         httpConn.setRequestProperty("SOAPAction", SOAPAction);
         httpConn.setRequestMethod("POST");
         httpConn.setDoOutput(true);
         OutputStream out = httpConn.getOutputStream();
         out.write(b);
         out.close();
         httpConn.connect();
         logger.debug("http connection status :" + httpConn.getResponseMessage());
         InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
         BufferedReader in = new BufferedReader(isr);
         String inputLine = "";

         String xmlResponse;
         for(xmlResponse = ""; (inputLine = in.readLine()) != null; xmlResponse = xmlResponse + inputLine + "\n") {
         }

         logger.debug("Xml response: " + prettyFormat(xmlResponse, 2));
         in.close();
         if (httpConn.getResponseCode() != 200) {
            errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde.";
         } else {
            try {
               MessageFactory mf = MessageFactory.newInstance();
               MimeHeaders header = new MimeHeaders();
               header.addHeader("Content-Type", "text/xml");
               InputStream fis = new ByteArrayInputStream(xmlResponse.getBytes());
               SOAPMessage soapMessage = mf.createMessage(header, fis);
               SOAPBody soapBody = soapMessage.getSOAPBody();
               if (soapBody.getElementsByTagName("Resultado").item(0).getTextContent().equals("R")) {
                  try {
                     NodeList nodes = soapBody.getElementsByTagName("Err");
                     Node node = nodes.item(0);
                     if (node != null) {
                        if ("10015".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "El documento ingresado no es válido";
                        } else if ("10016".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "La fecha del comprobante no es válida";
                        } else if ("10013".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "Para comprobantes clase A el tipo de documento debe ser CUIT";
                        } else if ("500".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde. (Cód. Error 500)";
                        } else if ("501".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde. (Cód. Error 501)";
                        } else if ("502".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde. (Cód. Error 502)";
                        } else if ("10000".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "La CUIT informada no está autorizada a emitir comprobantes clase A";
                        } else if ("10005".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "El Punto de Venta no está autorizado a emitir comprobantes electrónicos";
                        } else if (!"10061".equals(node.getChildNodes().item(0).getTextContent()) && !"10070".equals(node.getChildNodes().item(0).getTextContent())) {
                           errorMsg = node != null ? node.getChildNodes().item(0).getTextContent() : "";
                           errorMsg = errorMsg + " - ";
                           errorMsg = errorMsg + (node != null ? node.getChildNodes().item(1).getTextContent() : "");
                           errorMsg = errorMsg + " ";
                        } else {
                           errorMsg = "No se puede generar el CAE porque la venta contiene artículos con IVA 0%";
                        }
                     }

                     NodeList nodes2 = soapBody.getElementsByTagName("Obs");
                     Node node2 = nodes2.item(0);
                     if (node2 != null) {
                        if ("10015".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "El documento ingresado no es válido";
                        } else if ("10016".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "La fecha del comprobante no es válida";
                        } else if ("10013".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "Para comprobantes clase A el tipo de documento debe ser CUIT";
                        } else if ("500".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde. (Cód. Error 500)";
                        } else if ("501".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde. (Cód. Error 501)";
                        } else if ("502".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "No se pudo conectar con Afip. Por favor, intenta más tarde. (Cód. Error 502)";
                        } else if ("10000".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "La CUIT informada no está autorizada a emitir comprobantes clase A";
                        } else if ("10005".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = "El Punto de Venta no está autorizado a emitir comprobantes electrónicos";
                        } else if (!"10061".equals(node2.getChildNodes().item(0).getTextContent()) && !"10070".equals(node2.getChildNodes().item(0).getTextContent())) {
                           errorMsg = node2 != null ? node2.getChildNodes().item(0).getTextContent() : "";
                           errorMsg = errorMsg + " - ";
                           errorMsg = errorMsg + (node2 != null ? node2.getChildNodes().item(1).getTextContent() : "");
                           errorMsg = errorMsg + " ";
                        } else {
                           errorMsg = "No se puede generar el CAE porque la venta contiene artículos con IVA 0%";
                        }
                     }
                  } catch (Exception var27) {
                     errorMsg = "Se produjo un error desconocido.";
                     logger.error(var27.getMessage());
                     logger.error(var27.toString());
                  }

                  logger.info("Xml input: " + prettyFormat(xmlInput, 2));
                  logger.info("Xml response: " + prettyFormat(xmlResponse, 2));
               }

               if (soapBody.getElementsByTagName("Resultado").item(0).getTextContent().equals("A") || soapBody.getElementsByTagName("Resultado").item(0).getTextContent().equals("P")) {
                  try {
                     String afipCae = soapBody.getElementsByTagName("CAE").item(0).getTextContent();
                     String afipCaeFchVto = soapBody.getElementsByTagName("CAEFchVto").item(0).getTextContent();
                     notaDeCredito.setAfipCae(afipCae);
                     notaDeCredito.setAfipCaeFchVto(afipCaeFchVto);
                  } catch (Exception var26) {
                     logger.error(var26.getMessage());
                     logger.error(var26.toString());
                  }
               }
            } catch (Exception var28) {
               logger.error(var28.getMessage());
               logger.error(var28.toString());
            }
         }
      } catch (Exception var29) {
         logger.error(var29.getMessage());
         logger.error(var29.toString());
      }

      return errorMsg;
   }

   private String createCaeXmlEnvelopeForNotaDeCredito(NotaDeCredito notaDeCredito) {
      Long compNro = this.getCompUltimoAutorizadoAfip(notaDeCredito.getAfipCbteTipo()) + 1L;
      notaDeCredito.setAfipCbteDesde(compNro);
      notaDeCredito.setAfipCbteHasta(compNro);
      AppConfig appConfig = this.getAppConfigService().getAppConfig();
      AfipProp afipProp = this.getAppConfigService().getAfipProp();
      String xml = "";
      if (notaDeCredito.getAfipCbteTipo() == 13) {
         xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"                   xmlns:ar=\"" + this.getAfipConfig().getXmlnsAr() + "\">" + "<soapenv:Body>" + "<ar:FECAESolicitar>" + "<ar:Auth>" + "<ar:Token>" + afipProp.getAfipToken() + "</ar:Token>" + "<ar:Sign>" + afipProp.getAfipSign() + "</ar:Sign>" + "<ar:Cuit>" + appConfig.getCompanyCuit() + "</ar:Cuit>" + "</ar:Auth>" + "<ar:FeCAEReq>" + "<ar:FeCabReq>" + "<ar:CantReg>1</ar:CantReg>" + "<ar:PtoVta>" + notaDeCredito.getAfipPtoVta() + "</ar:PtoVta>" + "<ar:CbteTipo>" + notaDeCredito.getAfipCbteTipo() + "</ar:CbteTipo>" + "</ar:FeCabReq>" + "<ar:FeDetReq>" + "<ar:FECAEDetRequest>" + "<ar:Concepto>" + notaDeCredito.getAfipConcepto() + "</ar:Concepto>" + "<ar:DocTipo>" + notaDeCredito.getAfipDocTipo() + "</ar:DocTipo>" + "<ar:DocNro>" + notaDeCredito.getAfipDocNro() + "</ar:DocNro>" + "<ar:CbteDesde>" + notaDeCredito.getAfipCbteDesde() + "</ar:CbteDesde>" + "<ar:CbteHasta>" + notaDeCredito.getAfipCbteHasta() + "</ar:CbteHasta>" + "<ar:CbteFch>" + notaDeCredito.getAfipCbteFch() + "</ar:CbteFch>" + "<ar:ImpTotal>" + notaDeCredito.getTotalToAfip() + "</ar:ImpTotal>" + "<ar:ImpTotalConc>0</ar:ImpTotalConc>" + "<ar:ImpNeto>" + notaDeCredito.getTotalToAfip() + "</ar:ImpNeto>" + "<ar:ImpOpEx>0</ar:ImpOpEx>" + "<ar:ImpTrib>0</ar:ImpTrib>" + "<ar:ImpIVA>0</ar:ImpIVA>" + "<ar:FchServDesde>" + notaDeCredito.getAfipFchServDesde() + "</ar:FchServDesde>" + "<ar:FchServHasta>" + notaDeCredito.getAfipFchServHasta() + "</ar:FchServHasta>" + "<ar:FchVtoPago>" + notaDeCredito.getAfipFchVtoPago() + "</ar:FchVtoPago>" + "<ar:MonId>PES</ar:MonId>" + "<ar:MonCotiz>1</ar:MonCotiz>" + "</ar:FECAEDetRequest>" + "</ar:FeDetReq>" + "</ar:FeCAEReq>" + "</ar:FECAESolicitar>" + "</soapenv:Body>" + "</soapenv:Envelope>";
      } else if (notaDeCredito.getAfipCbteTipo() == 8 || notaDeCredito.getAfipCbteTipo() == 3) {
         xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"                   xmlns:ar=\"" + this.getAfipConfig().getXmlnsAr() + "\">" + "<soapenv:Body>" + "<ar:FECAESolicitar>" + "<ar:Auth>" + "<ar:Token>" + afipProp.getAfipToken() + "</ar:Token>" + "<ar:Sign>" + afipProp.getAfipSign() + "</ar:Sign>" + "<ar:Cuit>" + appConfig.getCompanyCuit() + "</ar:Cuit>" + "</ar:Auth>" + "<ar:FeCAEReq>" + "<ar:FeCabReq>" + "<ar:CantReg>1</ar:CantReg>" + "<ar:PtoVta>" + appConfig.getAfipPtoVta() + "</ar:PtoVta>" + "<ar:CbteTipo>" + notaDeCredito.getAfipCbteTipo() + "</ar:CbteTipo>" + "</ar:FeCabReq>" + "<ar:FeDetReq>" + "<ar:FECAEDetRequest>" + "<ar:Concepto>" + notaDeCredito.getAfipConcepto() + "</ar:Concepto>" + "<ar:DocTipo>" + notaDeCredito.getAfipDocTipo() + "</ar:DocTipo>" + "<ar:DocNro>" + notaDeCredito.getAfipDocNro() + "</ar:DocNro>" + "<ar:CbteDesde>" + notaDeCredito.getAfipCbteDesde() + "</ar:CbteDesde>" + "<ar:CbteHasta>" + notaDeCredito.getAfipCbteHasta() + "</ar:CbteHasta>" + "<ar:CbteFch>" + notaDeCredito.getAfipCbteFch() + "</ar:CbteFch>" + "<ar:ImpTotal>" + notaDeCredito.getTotalToAfip() + "</ar:ImpTotal>" + "<ar:ImpTotalConc>" + notaDeCredito.getImpTotalConcToAfip() + "</ar:ImpTotalConc>" + "<ar:ImpNeto>" + notaDeCredito.getImpNetoToAfip() + "</ar:ImpNeto>" + "<ar:ImpOpEx>0</ar:ImpOpEx>" + "<ar:ImpTrib>0</ar:ImpTrib>" + "<ar:ImpIVA>" + notaDeCredito.getImpIVAToAfip() + "</ar:ImpIVA>" + "<ar:FchServDesde>" + notaDeCredito.getAfipFchServDesde() + "</ar:FchServDesde>" + "<ar:FchServHasta>" + notaDeCredito.getAfipFchServHasta() + "</ar:FchServHasta>" + "<ar:FchVtoPago>" + notaDeCredito.getAfipFchVtoPago() + "</ar:FchVtoPago>" + "<ar:MonId>PES</ar:MonId>" + "<ar:MonCotiz>1</ar:MonCotiz>";
         if (notaDeCredito.hasAfipCbteAsoc()) {
            xml = xml + "<ar:CbtesAsoc><ar:CbteAsoc><ar:Tipo>" + notaDeCredito.getAfipCbteAsocTipo() + "</ar:Tipo>" + "<ar:PtoVta>" + notaDeCredito.getAfipCbteAsocPtoVta() + "</ar:PtoVta>" + "<ar:Nro>" + notaDeCredito.getAfipCbteAsocNro() + "</ar:Nro>" + "</ar:CbteAsoc>" + "</ar:CbtesAsoc>";
         }

         xml = xml + "<ar:Iva>";
         if (notaDeCredito.getImporteIVA105() > 0.0) {
            xml = xml + "<ar:AlicIva><ar:Id>4</ar:Id><ar:BaseImp>" + notaDeCredito.getBaseImpIVA105ToAfip() + "</ar:BaseImp>" + "<ar:Importe>" + notaDeCredito.getImporteIVA105ToAfip() + "</ar:Importe>" + "</ar:AlicIva>";
         }

         if (notaDeCredito.getImporteIVA21() > 0.0) {
            xml = xml + "<ar:AlicIva><ar:Id>5</ar:Id><ar:BaseImp>" + notaDeCredito.getBaseImpIVA21ToAfip() + "</ar:BaseImp>" + "<ar:Importe>" + notaDeCredito.getImporteIVA21ToAfip() + "</ar:Importe>" + "</ar:AlicIva>";
         }

         xml = xml + "</ar:Iva></ar:FECAEDetRequest></ar:FeDetReq></ar:FeCAEReq></ar:FECAESolicitar></soapenv:Body></soapenv:Envelope>";
      }

      return xml;
   }

   public String getAfipStatus(AppConfig appConfig) {
      String afipStatus = "No Disponible";

      try {
         int cbteTipo = 0;
         if (appConfig.isMonotributo()) {
            cbteTipo = 11;
         } else if (appConfig.isResponsableInscripto()) {
            cbteTipo = 6;
         }

         if (cbteTipo != 0) {
            Long ultimo = this.getCompUltimoAutorizadoAfip(cbteTipo);
            if (ultimo == -1L) {
               this.getAppConfigService().loginAfip();
               ultimo = this.getCompUltimoAutorizadoAfip(cbteTipo);
            }

            if (ultimo != -1L) {
               afipStatus = "Disponible";
            }
         }
      } catch (Exception var5) {
      }

      return afipStatus;
   }
}
