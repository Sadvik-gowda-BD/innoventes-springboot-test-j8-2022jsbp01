package com.innoventes.test.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.innoventes.test.app.dto.CompanyDTO;
import com.innoventes.test.app.dto.PatchDto;
import com.innoventes.test.app.mapper.CompanyMapper;
import com.innoventes.test.app.validator.CompanyRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innoventes.test.app.entity.Company;
import com.innoventes.test.app.error.ApplicationErrorCodes;
import com.innoventes.test.app.exception.ResourceNotFoundException;
import com.innoventes.test.app.exception.ValidationException;
import com.innoventes.test.app.repository.CompanyRepository;
import com.innoventes.test.app.service.CompanyService;
import com.innoventes.test.app.util.ServiceHelper;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ServiceHelper serviceHelper;

	@Autowired
	private CompanyMapper companyMapper;

	@Override
	public List<Company> getAllCompanies() {
		ArrayList<Company> companyList = new ArrayList<Company>();
		companyRepository.findAll().forEach(companyList::add);
		return companyList;
	}

	@Override
	public Company addCompany(Company company) throws ValidationException {
		CompanyRequestValidator.companyCodeValidator(company);
		if (!CompanyRequestValidator.urlValidator(company.getWebSiteURL())) {
			company.setWebSiteURL(null);
		}
		return companyRepository.save(company);
	}

	@Override
	public Company updateCompany(Long id, Company company) throws ValidationException {
		Company existingCompanyRecord = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(serviceHelper.getLocalizedMessage(ApplicationErrorCodes.COMPANY_NOT_FOUND), id),
						ApplicationErrorCodes.COMPANY_NOT_FOUND));
		company.setId(existingCompanyRecord.getId());
		return companyRepository.save(company);
	}

	@Override
	public void deleteCompany(Long id) {
		Company existingCompanyRecord = companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(serviceHelper.getLocalizedMessage(ApplicationErrorCodes.COMPANY_NOT_FOUND), id),
						ApplicationErrorCodes.COMPANY_NOT_FOUND));
		companyRepository.deleteById(existingCompanyRecord.getId());
	}

	@Override
	public CompanyDTO getCompanyById(Long id) {
		Company company = getById(id);
		return companyMapper.getCompanyDTO(company);
	}

	@Override
	public CompanyDTO getCompanyByCompanyCode(String companyCode) {
		Company company = companyRepository.findByCompanyCode(companyCode)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(serviceHelper.getLocalizedMessage(ApplicationErrorCodes.COMPANY_NOT_FOUND), companyCode),
						ApplicationErrorCodes.COMPANY_NOT_FOUND));
		return companyMapper.getCompanyDTO(company);
	}

	@Override
	public CompanyDTO partialUpdate(PatchDto patchDto, Long id) {
		Company company = getById(id);
		companyMapper.partialMap(company, patchDto);
		companyRepository.save(company);
		return companyMapper.getCompanyDTO(company);
	}

	private Company getById(Long id) {
		return companyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(serviceHelper.getLocalizedMessage(ApplicationErrorCodes.COMPANY_NOT_FOUND), id),
						ApplicationErrorCodes.COMPANY_NOT_FOUND));
	}
}
