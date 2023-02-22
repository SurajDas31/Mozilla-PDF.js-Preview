package com.docedge.esign.service;


import javax.security.auth.DestroyFailedException;
import java.io.File;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;


public interface SignService {
    File getSigned(File inputFile, File resultFile, String pass, String reason, String location) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, IOException, DestroyFailedException;
}
