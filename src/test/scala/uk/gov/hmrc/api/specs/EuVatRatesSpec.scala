/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.api.specs

import org.scalatest.Assertion

import scala.xml.{Node, XML}

class EuVatRatesSpec extends BaseSpec {

  Feature("Retrieving EU Vat Rates") {

    Scenario("Retrieve Vat Rates from EC for a given country and dates") {

      When("The EC Vat Rates API is called")

      val response =
        euVatRatesService.getEuVatRatesDateRange("CZ", "2023-11-01", "2023-11-30")

      Then("The vat rates are returned in the correct format")

      val loadedXml = XML.loadString(response.body)

      val listOfItems = loadedXml \\ "Envelope" \\ "Body" \\ "retrieveVatRatesRespMsg" \\ "vatRateResults"

      response.status shouldBe 200

      listOfItems.size shouldBe 37

      listOfItems.foreach(singleItem => checkForSingleEuVatRate(singleItem))

    }

  }

  def checkForSingleEuVatRate(elem: Node): Assertion = {

    val memberStateElem = elem \ "memberState"
    val rateElem        = elem \ "rate" \ "value"
    val vatRateTypeElem = elem \ "type"
    val situatedOnElem  = elem \ "situationOn"

    memberStateElem.exists(x => x.text.nonEmpty) shouldBe true
    rateElem.exists(x => x.text.nonEmpty)        shouldBe true
    vatRateTypeElem.exists(x => x.text.nonEmpty) shouldBe true
    situatedOnElem.exists(x => x.text.nonEmpty)  shouldBe true

  }
}
