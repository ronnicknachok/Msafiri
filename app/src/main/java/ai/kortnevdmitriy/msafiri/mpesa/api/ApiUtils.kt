/*
 *
 *  * Copyright (C) 2017 Safaricom, Ltd.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package ai.kortnevdmitriy.msafiri.mpesa.api


import ai.kortnevdmitriy.msafiri.mpesa.api.services.STKPushService


object ApiUtils {
	private const val BASE_URL = "https://sandbox.safaricom.co.ke/"
	
	fun getTasksService(token: String): STKPushService {
		return RetrofitClient.getClient(BASE_URL, token).create(STKPushService::class.java)
	}
}
