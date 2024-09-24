package com.wavebank.wave.event.listener;

import com.wavebank.wave.event.RegistrationCompleteEvent;
import org.springframework.context.ApplicationListener;

public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // 1. Get the newly registered user
        // 2. Create a verification token for the user
        // 3. Save the verification token for the user
        // 4. Build the verification url to be sent to the user
        // 5. Send the email.
    }
}
