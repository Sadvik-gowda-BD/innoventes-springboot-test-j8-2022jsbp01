package com.innoventes.test.app.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.innoventes.test.app.dto.PatchDto;
import org.springframework.stereotype.Component;

import com.innoventes.test.app.dto.CompanyDTO;
import com.innoventes.test.app.entity.Company;

@Component
public class CompanyMapperImpl implements CompanyMapper {

	@Override
	public CompanyDTO getCompanyDTO(Company entity) {
		CompanyDTO dto = new CompanyDTO();
		
		dto.setId(entity.getId());
		dto.setCompanyName(entity.getCompanyName());
		dto.setEmail(entity.getEmail());
		dto.setStrength(entity.getStrength());
		dto.setWebSiteURL(entity.getWebSiteURL());
		
		return dto;
	}

    @Override
    public List<CompanyDTO> getCompanyDTOList(List<Company> entityList) {
        List<CompanyDTO> dtoList = new ArrayList<CompanyDTO>();

        for (Company entity : entityList) {
            dtoList.add(getCompanyDTO(entity));
        }

        return dtoList;
    }

    @Override
    public Set<CompanyDTO> getCompanyDTOSet(Set<Company> entitySet) {
        Set<CompanyDTO> dtoSet = new HashSet<CompanyDTO>();

        for (Company entity : entitySet) {
            dtoSet.add(getCompanyDTO(entity));
        }

        return dtoSet;
    }

    @Override
    public Company getCompany(CompanyDTO dto) {
        Company entity = new Company();
        entity.setId(dto.getId());
        entity.setCompanyName(dto.getCompanyName());
        entity.setEmail(dto.getEmail());
        entity.setStrength(dto.getStrength());
        entity.setWebSiteURL(dto.getWebSiteURL());
        return entity;
    }

    @Override
    public void partialMap(Company company, PatchDto patchDto) {
        List<String> fieldName = patchDto.getFieldName();
        List<String> fieldValue = patchDto.getFieldValue();

        if (fieldName.size() != fieldValue.size()) {
			//TODO can be change to custom exception with proper error codes
            throw new RuntimeException("Given field and values are not matching");
        }

        for (int i = 0; i < fieldName.size(); i++) {
            mapField(fieldName.get(i), fieldValue.get(i), company);
        }
    }

    private void mapField(String fieldName, String fieldValue, Company company) {

        //TODO: Change to switch for better performance
        if ("companyName".equals(fieldName)) {
            company.setCompanyName(fieldValue);
        } else if ("email".equals(fieldName)) {
            company.setEmail(fieldValue);
        } else if ("strength".equals(fieldName)) {
            company.setStrength(Integer.valueOf(fieldValue));
        } else if ("website_url".equals(fieldName)) {
            company.setWebSiteURL(fieldValue);
        } else if ("company_code".equals(fieldName)) {
            company.setCompanyCode(fieldValue);
        } else {
            //TODO: can be changed to custom exception with proper error codes
            throw new RuntimeException("Invalid field");
        }
    }
}
