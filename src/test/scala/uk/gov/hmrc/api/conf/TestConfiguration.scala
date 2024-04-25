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

package uk.gov.hmrc.api.conf

import com.typesafe.config.{Config, ConfigFactory}

object TestConfiguration {
  val config: Config         = ConfigFactory.load()
  val servicesConfig: Config = config.getConfig("services")

  def url(service: String): String =
    s"${environmentProtocol(service)}://${environmentHost(service)}:${servicePort(service)}${serviceRoute(service)}"

  def environmentProtocol(serviceName: String): String = servicesConfig.getString(s"$serviceName.protocol")

  def environmentHost(serviceName: String): String = servicesConfig.getString(s"$serviceName.host")

  def servicePort(serviceName: String): String = servicesConfig.getString(s"$serviceName.port")

  def serviceRoute(serviceName: String): String = servicesConfig.getString(s"$serviceName.productionRoute")
}
