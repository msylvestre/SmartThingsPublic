/**
 *  My first smart ass
 *
 *  Copyright 2017 Marco Sylvestre
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Light Timer",
    namespace: "msylvestre",
    author: "Marco Sylvestre",
    description: "Let a light goes ON at a certain time, for one time.  Likely to be used as a timer in the kitchen; 'when the potatos are ready, the light goes ON :-)'",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {

	section("Run it now ?") {
        input "runit", "enum", title: "Yes/No", displayDuringSetup: false, 
              required: true, options: ["No", "Yes"], defaultValue: "Yes"
	}

    section("Turn on this light") {
        input "theswitch", "capability.switchLevel", required: true, multiple: true
    }

    section("At this time to alert me") {
        input "starttime", "time", title: "Time", required: false
	}    
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
    log.debug "Time of the device : ${now()}"
    runOnce(starttime, execute)
    subscribe(app, appTouch)
}

def appTouch(evt) {
	log.debug "appTouch: $evt"
	theswitch.setLevel(50)

    def lvl = theswitch.currentValue("level")
    log.info("Dimmer level: ${lvl}")
}

def execute() {
	log.debug "execute() method has been executed and runit param is ${runit}";

    if (runit == "Yes") {
    
        theswitch.setLevel(99) // 99 is the new 100 !
    
        def lvl = theswitch.currentValue("level")
        log.info("Dimmer level: ${lvl}") 
    }
}
