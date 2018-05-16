/*
 * Copyright (C) 2017 Samsung Electronics Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package skku.swprac3.modeola;

class ArtikConfig {
    static final String CLIENT_ID = "e9c5b176f1a2487098841326be9a67c6"; // AKA application ID
    static final String DEVICE_ID = "ba0e815389cc49b78bfa3e092c8c80cd";

    // MUST be consistent with "AUTH REDIRECT URL" of your application set up at the developer.artik.cloud
    static final String REDIRECT_URI = "skku.swprac3.modeola://oauth2callback";
    static final String test = "CommitTest";

}
