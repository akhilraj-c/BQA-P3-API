package com.mindteck.common.modules.user.util;

import com.mindteck.common.models.InstituteForm;
import com.mindteck.common.repository.InstitutionFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.OptionalLong;
import java.util.Random;

@Component
public class UserUtils {

    public Long generateUniqueId() {
        final Date now = new Date();
        final SimpleDateFormat ft = new SimpleDateFormat("ssMs");
        final Random random = new Random();
        int randomOne = random.nextInt(500);
        long randomTwo = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        long userDisplayId = randomOne + randomTwo + Long.parseLong(ft.format(now));
        return userDisplayId;
    }

    @Autowired
    private InstitutionFormRepository institutionFormRepository;

    public String generateUniqueQpId() {
        int currentYear = Year.now().getValue() % 100;

        String yearFormat = "Q"+currentYear+"-";
        List<InstituteForm> instituteForms = institutionFormRepository.findByQpIdLike(yearFormat+"%");

        OptionalLong maxNumber = instituteForms.stream()
                .map(InstituteForm::getQpId) // Get the qp_id string
                .map(id -> id.replace(yearFormat, "")) // Remove the year prefix
                .mapToLong(Long::parseLong) // Convert to integer
                .max();

        String uniqueId;
        long lastGeneratedNumber =  maxNumber.orElse(0);
        do {
            lastGeneratedNumber ++;
            String formattedNumber = String.format(lastGeneratedNumber > 999 ? "%d" : "%03d", lastGeneratedNumber);
            uniqueId = yearFormat + formattedNumber;
        } while (institutionFormRepository.findByQpId(uniqueId).isPresent());
        return uniqueId;
    }
}
