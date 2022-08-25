package com.github.he305.contentcore.contentaccount.domain.service;

import com.github.he305.contentcore.contentaccount.domain.exceptions.ContentAccountVerifierException;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;

public interface ContentAccountVerifierService {
    void verify(ContentAccountDetails details) throws ContentAccountVerifierException;
}
