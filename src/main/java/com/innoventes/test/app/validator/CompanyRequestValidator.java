package com.innoventes.test.app.validator;

import com.innoventes.test.app.entity.Company;

import javax.validation.ValidationException;

public class CompanyRequestValidator {

    public static void companyCodeValidator(Company company) {
        String code = company.getCompanyCode();
        if (code != null) {
            boolean isvalidLength = code.length() == 5;
            if (!isvalidLength || !isValidCompanyCode(company.getCompanyCode())) {
                throw new ValidationException("Invalid company code");
            }
        }
    }

    private static boolean isValidCompanyCode(String code) {
        return Character.isAlphabetic(code.charAt(0))
                && Character.isAlphabetic(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isDigit(code.charAt(3))
                && isCharEndsWithEorN(code);
    }

    private static boolean isCharEndsWithEorN(String code) {
        return Character.toUpperCase(code.charAt(4)) == 'E'
                || Character.toUpperCase(code.charAt(4)) == 'N';
    }

    public static boolean urlValidator(String url) {
        //TODO logic to validate url
        return false;
    }
}
