package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum Fields {

    BPQ(1, "Basic Programmes and Qualifications"),
    LAW(2, "Law"),
    FORESTRY(3, "Forestry"),
    L_AND_N(4, "Literacy and numeracy"),
    B_AND_RS(5, "Biological and related sciences"),

    FISHERIES(6, "Fisheries"),

    PS_AND_D(7, "Personal skills and development"),
    ENVIRONMENT(8, "Environment"),

    VETERINARY(9, "Veterinary"),

    EDUCATION(10, "Education"),
    PHYSICAL_SCIENCE(11, "physical sciences"),

    HEALTH(12, "Health"),

    ARTS(13, "Arts"),

    MATHEMATICS_AND_STATISTICS(14, "Mathematics and statistics"),

    WELFARE(15, "Welfare"),

    HUMANITIES(16, "Humanities (except language)"),

    INFORMATION_AND_COMMUNICATION_TECHNOLOGIES(17, "Information and communication technologies"),

    PERSONAL_SERVICES(18, "Personal services"),

    LANGUAGES(19, "Languages"),

    ENGINEERING_AND_ENGINEERING_TRADES(20, "Engineering and engineering trades"),

    HYGIENE_AND_OCCUPATIONAL_HEALTH_SERVICES(21, "Hygiene and occupational health services"),

    SOCIAL_AND_BEHAVIOURAL_SCIENCES(22, "Social and behavioural sciences"),

    MANUFACTURING_AND_PROCESSING(23, "Manufacturing and processing"),

    SECURITY_SERVICES(24, "Security services"),

    JOURNALISM_AND_INFORMATION(25, "Journalism and information"),

    ARCHITECTURE_AND_CONSTRUCTION(26, "Architecture and construction"),

    TRANSPORT_SERVICES(27, "Transport services"),

    BUSINESS_AND_ADMINISTRATION(28, "Business and administration"),

    AGRICULTURE(29, "Agriculture"),

    OTHERS(30, "Others");


    private final Integer code;
    private final String name;
}
