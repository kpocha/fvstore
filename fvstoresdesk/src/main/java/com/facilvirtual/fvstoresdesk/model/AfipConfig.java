package com.facilvirtual.fvstoresdesk.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AfipConfig {
   @Value("${afipConfig.devMode}")
   private String devMode = "1";
   @Value("${afipConfig.login.endpointUrl.dev}")
   private String loginEndpointUrlDev;
   @Value("${afipConfig.login.service.dev}")
   private String loginServiceDev;
   @Value("${afipConfig.login.dstdn.dev}")
   private String loginDstdnDev;
   @Value("${afipConfig.login.keystore.dev}")
   private String loginKeystoreDev;
   @Value("${afipConfig.login.keystoreSigner.dev}")
   private String loginKeystoreSignerDev;
   @Value("${afipConfig.login.keystorePassword.dev}")
   private String loginKeystorePasswordDev;
   @Value("${afipConfig.login.proxyHost.dev}")
   private String loginProxyHostDev;
   @Value("${afipConfig.login.proxyPort.dev}")
   private String loginProxyPortDev;
   @Value("${afipConfig.login.proxyUser.dev}")
   private String loginProxyUserDev;
   @Value("${afipConfig.login.proxyPassword.dev}")
   private String loginProxyPasswordDev;
   @Value("${afipConfig.login.trustStore.dev}")
   private String loginTrustStoreDev;
   @Value("${afipConfig.login.trustStorePassword.dev}")
   private String loginTrustStorePasswordDev;
   @Value("${afipConfig.login.ticketGenTime.dev}")
   private String loginTicketGenTimeDev;
   @Value("${afipConfig.login.ticketExpTime.dev}")
   private String loginTicketExpTimeDev;
   @Value("${afipConfig.endpointUrl.dev}")
   private String endpointUrlDev;
   @Value("${afipConfig.xmlns.ar.dev}")
   private String xmlnsArDev;
   @Value("${afipConfig.SOAPAction.FECompUltimoAutorizado.dev}")
   private String SOAPActionFECompUltimoAutorizadoDev;
   @Value("${afipConfig.SOAPAction.FECAESolicitar.dev}")
   private String SOAPActionFECAESolicitarDev;
   @Value("${afipConfig.login.endpointUrl.prod}")
   private String loginEndpointUrlProd;
   @Value("${afipConfig.login.service.prod}")
   private String loginServiceProd;
   @Value("${afipConfig.login.dstdn.prod}")
   private String loginDstdnProd;
   @Value("${afipConfig.login.keystore.prod}")
   private String loginKeystoreProd;
   @Value("${afipConfig.login.keystoreSigner.prod}")
   private String loginKeystoreSignerProd;
   @Value("${afipConfig.login.keystorePassword.prod}")
   private String loginKeystorePasswordProd;
   @Value("${afipConfig.login.proxyHost.prod}")
   private String loginProxyHostProd;
   @Value("${afipConfig.login.proxyPort.prod}")
   private String loginProxyPortProd;
   @Value("${afipConfig.login.proxyUser.prod}")
   private String loginProxyUserProd;
   @Value("${afipConfig.login.proxyPassword.prod}")
   private String loginProxyPasswordProd;
   @Value("${afipConfig.login.trustStore.prod}")
   private String loginTrustStoreProd;
   @Value("${afipConfig.login.trustStorePassword.prod}")
   private String loginTrustStorePasswordProd;
   @Value("${afipConfig.login.ticketGenTime.prod}")
   private String loginTicketGenTimeProd;
   @Value("${afipConfig.login.ticketExpTime.prod}")
   private String loginTicketExpTimeProd;
   @Value("${afipConfig.endpointUrl.prod}")
   private String endpointUrlProd;
   @Value("${afipConfig.xmlns.ar.prod}")
   private String xmlnsArProd;
   @Value("${afipConfig.SOAPAction.FECompUltimoAutorizado.prod}")
   private String SOAPActionFECompUltimoAutorizadoProd;
   @Value("${afipConfig.SOAPAction.FECAESolicitar.prod}")
   private String SOAPActionFECAESolicitarProd;

   public AfipConfig() {
   }

   public String getSOAPActionFECAESolicitarDev() {
      return this.SOAPActionFECAESolicitarDev;
   }

   public void setSOAPActionFECAESolicitarDev(String sOAPActionFECAESolicitarDev) {
      this.SOAPActionFECAESolicitarDev = sOAPActionFECAESolicitarDev;
   }

   public String getSOAPActionFECAESolicitarProd() {
      return this.SOAPActionFECAESolicitarProd;
   }

   public void setSOAPActionFECAESolicitarProd(String sOAPActionFECAESolicitarProd) {
      this.SOAPActionFECAESolicitarProd = sOAPActionFECAESolicitarProd;
   }

   public String getLoginServiceDev() {
      return this.loginServiceDev;
   }

   public void setLoginServiceDev(String loginServiceDev) {
      this.loginServiceDev = loginServiceDev;
   }

   public String getLoginDstdnDev() {
      return this.loginDstdnDev;
   }

   public void setLoginDstdnDev(String loginDstdnDev) {
      this.loginDstdnDev = loginDstdnDev;
   }

   public String getLoginKeystoreDev() {
      return this.loginKeystoreDev;
   }

   public void setLoginKeystoreDev(String loginKeystoreDev) {
      this.loginKeystoreDev = loginKeystoreDev;
   }

   public String getLoginKeystoreSignerDev() {
      return this.loginKeystoreSignerDev;
   }

   public void setLoginKeystoreSignerDev(String loginKeystoreSignerDev) {
      this.loginKeystoreSignerDev = loginKeystoreSignerDev;
   }

   public String getLoginKeystorePasswordDev() {
      return this.loginKeystorePasswordDev;
   }

   public void setLoginKeystorePasswordDev(String loginKeystorePasswordDev) {
      this.loginKeystorePasswordDev = loginKeystorePasswordDev;
   }

   public String getLoginServiceProd() {
      return this.loginServiceProd;
   }

   public void setLoginServiceProd(String loginServiceProd) {
      this.loginServiceProd = loginServiceProd;
   }

   public String getLoginDstdnProd() {
      return this.loginDstdnProd;
   }

   public void setLoginDstdnProd(String loginDstdnProd) {
      this.loginDstdnProd = loginDstdnProd;
   }

   public String getLoginKeystoreProd() {
      return this.loginKeystoreProd;
   }

   public void setLoginKeystoreProd(String loginKeystoreProd) {
      this.loginKeystoreProd = loginKeystoreProd;
   }

   public String getLoginKeystoreSignerProd() {
      return this.loginKeystoreSignerProd;
   }

   public void setLoginKeystoreSignerProd(String loginKeystoreSignerProd) {
      this.loginKeystoreSignerProd = loginKeystoreSignerProd;
   }

   public String getLoginKeystorePasswordProd() {
      return this.loginKeystorePasswordProd;
   }

   public void setLoginKeystorePasswordProd(String loginKeystorePasswordProd) {
      this.loginKeystorePasswordProd = loginKeystorePasswordProd;
   }

   public String getLoginEndpointUrlDev() {
      return this.loginEndpointUrlDev;
   }

   public void setLoginEndpointUrlDev(String loginEndpointUrlDev) {
      this.loginEndpointUrlDev = loginEndpointUrlDev;
   }

   public String getLoginEndpointUrlProd() {
      return this.loginEndpointUrlProd;
   }

   public void setLoginEndpointUrlProd(String loginEndpointUrlProd) {
      this.loginEndpointUrlProd = loginEndpointUrlProd;
   }

   public String getEndpointUrlDev() {
      return this.endpointUrlDev;
   }

   public void setEndpointUrlDev(String endpointUrlDev) {
      this.endpointUrlDev = endpointUrlDev;
   }

   public String getDevMode() {
      return this.devMode;
   }

   public void setDevMode(String devMode) {
      this.devMode = devMode;
   }

   public String getEndpointUrlProd() {
      return this.endpointUrlProd;
   }

   public void setEndpointUrlProd(String endpointUrlProd) {
      this.endpointUrlProd = endpointUrlProd;
   }

   public String getXmlnsArDev() {
      return this.xmlnsArDev;
   }

   public void setXmlnsArDev(String xmlnsArDev) {
      this.xmlnsArDev = xmlnsArDev;
   }

   public String getXmlnsArProd() {
      return this.xmlnsArProd;
   }

   public void setXmlnsArProd(String xmlnsArProd) {
      this.xmlnsArProd = xmlnsArProd;
   }

   public String getSOAPActionFECompUltimoAutorizadoDev() {
      return this.SOAPActionFECompUltimoAutorizadoDev;
   }

   public void setSOAPActionFECompUltimoAutorizadoDev(String sOAPActionFECompUltimoAutorizadoDev) {
      this.SOAPActionFECompUltimoAutorizadoDev = sOAPActionFECompUltimoAutorizadoDev;
   }

   public String getSOAPActionFECompUltimoAutorizadoProd() {
      return this.SOAPActionFECompUltimoAutorizadoProd;
   }

   public void setSOAPActionFECompUltimoAutorizadoProd(String sOAPActionFECompUltimoAutorizadoProd) {
      this.SOAPActionFECompUltimoAutorizadoProd = sOAPActionFECompUltimoAutorizadoProd;
   }

   public String getLoginProxyHostDev() {
      return this.loginProxyHostDev;
   }

   public void setLoginProxyHostDev(String loginProxyHostDev) {
      this.loginProxyHostDev = loginProxyHostDev;
   }

   public String getLoginProxyPortDev() {
      return this.loginProxyPortDev;
   }

   public void setLoginProxyPortDev(String loginProxyPortDev) {
      this.loginProxyPortDev = loginProxyPortDev;
   }

   public String getLoginProxyUserDev() {
      return this.loginProxyUserDev;
   }

   public void setLoginProxyUserDev(String loginProxyUserDev) {
      this.loginProxyUserDev = loginProxyUserDev;
   }

   public String getLoginProxyPasswordDev() {
      return this.loginProxyPasswordDev;
   }

   public void setLoginProxyPasswordDev(String loginProxyPasswordDev) {
      this.loginProxyPasswordDev = loginProxyPasswordDev;
   }

   public String getLoginTrustStoreDev() {
      return this.loginTrustStoreDev;
   }

   public void setLoginTrustStoreDev(String loginTrustStoreDev) {
      this.loginTrustStoreDev = loginTrustStoreDev;
   }

   public String getLoginTrustStorePasswordDev() {
      return this.loginTrustStorePasswordDev;
   }

   public void setLoginTrustStorePasswordDev(String loginTrustStorePasswordDev) {
      this.loginTrustStorePasswordDev = loginTrustStorePasswordDev;
   }

   public String getLoginTicketGenTimeDev() {
      return this.loginTicketGenTimeDev;
   }

   public void setLoginTicketGenTimeDev(String loginTicketGenTimeDev) {
      this.loginTicketGenTimeDev = loginTicketGenTimeDev;
   }

   public String getLoginTicketExpTimeDev() {
      return this.loginTicketExpTimeDev;
   }

   public void setLoginTicketExpTimeDev(String loginTicketExpTimeDev) {
      this.loginTicketExpTimeDev = loginTicketExpTimeDev;
   }

   public String getLoginProxyHostProd() {
      return this.loginProxyHostProd;
   }

   public void setLoginProxyHostProd(String loginProxyHostProd) {
      this.loginProxyHostProd = loginProxyHostProd;
   }

   public String getLoginProxyPortProd() {
      return this.loginProxyPortProd;
   }

   public void setLoginProxyPortProd(String loginProxyPortProd) {
      this.loginProxyPortProd = loginProxyPortProd;
   }

   public String getLoginProxyUserProd() {
      return this.loginProxyUserProd;
   }

   public void setLoginProxyUserProd(String loginProxyUserProd) {
      this.loginProxyUserProd = loginProxyUserProd;
   }

   public String getLoginProxyPasswordProd() {
      return this.loginProxyPasswordProd;
   }

   public void setLoginProxyPasswordProd(String loginProxyPasswordProd) {
      this.loginProxyPasswordProd = loginProxyPasswordProd;
   }

   public String getLoginTrustStoreProd() {
      return this.loginTrustStoreProd;
   }

   public void setLoginTrustStoreProd(String loginTrustStoreProd) {
      this.loginTrustStoreProd = loginTrustStoreProd;
   }

   public String getLoginTrustStorePasswordProd() {
      return this.loginTrustStorePasswordProd;
   }

   public void setLoginTrustStorePasswordProd(String loginTrustStorePasswordProd) {
      this.loginTrustStorePasswordProd = loginTrustStorePasswordProd;
   }

   public String getLoginTicketGenTimeProd() {
      return this.loginTicketGenTimeProd;
   }

   public void setLoginTicketGenTimeProd(String loginTicketGenTimeProd) {
      this.loginTicketGenTimeProd = loginTicketGenTimeProd;
   }

   public String getLoginTicketExpTimeProd() {
      return this.loginTicketExpTimeProd;
   }

   public void setLoginTicketExpTimeProd(String loginTicketExpTimeProd) {
      this.loginTicketExpTimeProd = loginTicketExpTimeProd;
   }

   public String getEndpointUrl() {
      return "1".equals(this.getDevMode()) ? this.getEndpointUrlDev() : this.getEndpointUrlProd();
   }

   public String getSOAPActionFECompUltimoAutorizado() {
      return "1".equals(this.getDevMode()) ? this.getSOAPActionFECompUltimoAutorizadoDev() : this.getSOAPActionFECompUltimoAutorizadoProd();
   }

   public String getXmlnsAr() {
      return "1".equals(this.getDevMode()) ? this.getXmlnsArDev() : this.getXmlnsArProd();
   }

   public String getLoginEndpointUrl() {
      return "1".equals(this.getDevMode()) ? this.getLoginEndpointUrlDev() : this.getLoginEndpointUrlProd();
   }

   public String getLoginService() {
      return "1".equals(this.getDevMode()) ? this.getLoginServiceDev() : this.getLoginServiceProd();
   }

   public String getLoginDstdn() {
      return "1".equals(this.getDevMode()) ? this.getLoginDstdnDev() : this.getLoginDstdnProd();
   }

   public String getLoginKeystore() {
      return "1".equals(this.getDevMode()) ? this.getLoginKeystoreDev() : this.getLoginKeystoreProd();
   }

   public String getLoginKeystoreSigner() {
      return "1".equals(this.getDevMode()) ? this.getLoginKeystoreSignerDev() : this.getLoginKeystoreSignerProd();
   }

   public String getLoginKeystorePassword() {
      return "1".equals(this.getDevMode()) ? this.getLoginKeystorePasswordDev() : this.getLoginKeystorePasswordProd();
   }

   public String getSOAPActionFECAESolicitar() {
      return "1".equals(this.getDevMode()) ? this.getSOAPActionFECAESolicitarDev() : this.getSOAPActionFECAESolicitarProd();
   }

   public String getLoginProxyHost() {
      return "1".equals(this.getDevMode()) ? this.getLoginProxyHostDev() : this.getLoginProxyHostProd();
   }

   public String getLoginProxyPort() {
      return "1".equals(this.getDevMode()) ? this.getLoginProxyPortDev() : this.getLoginProxyPortProd();
   }

   public String getLoginProxyUser() {
      return "1".equals(this.getDevMode()) ? this.getLoginProxyUserDev() : this.getLoginProxyUserProd();
   }

   public String getLoginProxyPassword() {
      return "1".equals(this.getDevMode()) ? this.getLoginProxyPasswordDev() : this.getLoginProxyPasswordProd();
   }

   public String getLoginTrustStore() {
      return "1".equals(this.getDevMode()) ? this.getLoginTrustStoreDev() : this.getLoginTrustStoreProd();
   }

   public String getLoginTrustStorePassword() {
      return "1".equals(this.getDevMode()) ? this.getLoginTrustStorePasswordDev() : this.getLoginTrustStorePasswordProd();
   }

   public String getLoginTicketGenTime() {
      return "1".equals(this.getDevMode()) ? this.getLoginTicketGenTimeDev() : this.getLoginTicketGenTimeProd();
   }

   public String getLoginTicketExpTime() {
      return "1".equals(this.getDevMode()) ? this.getLoginTicketExpTimeDev() : this.getLoginTicketExpTimeProd();
   }
}
