/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.api.service

import play.api.libs.ws.StandaloneWSRequest
import uk.gov.hmrc.api.client.HttpClient
import uk.gov.hmrc.api.conf.TestConfiguration

import scala.concurrent.Await
import scala.concurrent.duration._

class EuVatRatesService extends HttpClient {
  val host: String          = TestConfiguration.url("ec-vat-rates")
  val ecVatRatesURL: String = s"$host/taxation_customs/tedb/ws/VatRetrievalService.wsdl"

  def getEuVatRatesDateRange(
    countryCode: String,
    startDate: String,
    endDate: String
  ): StandaloneWSRequest#Self#Response =
    Await.result(
      post(
        ecVatRatesURL,
        (<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:ec.europa.eu:taxud:tedb:services:v1:IVatRetrievalService" xmlns:urn1="urn:ec.europa.eu:taxud:tedb:services:v1:IVatRetrievalService:types">
          <soapenv:Header/>
          <soapenv:Body>
            <urn:retrieveVatRatesReqMsg>
              <urn1:memberStates>
                <urn1:isoCode>{countryCode}</urn1:isoCode>
              </urn1:memberStates>
              <urn1:from>{startDate}</urn1:from>
              <urn1:to>{endDate}</urn1:to>
            </urn:retrieveVatRatesReqMsg>
          </soapenv:Body>
        </soapenv:Envelope>).toString(),
        "SOAPAction"   -> "urn:ec.europa.eu:taxud:tedb:services:v1:VatRetrievalService/RetrieveVatRates",
        "Content-Type" -> "text/xml"
      ),
      10.seconds
    )

}
