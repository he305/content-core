package com.github.he305.contentcore.contentaccount.domain.exceptions;

import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.shared.exceptions.ContentCoreException;

public class ContentAccountVerifierException extends ContentCoreException {
    public ContentAccountVerifierException(ContentAccountDetails details) {
        super("Provided content account details not verified, " + details.toString());
    }
}
