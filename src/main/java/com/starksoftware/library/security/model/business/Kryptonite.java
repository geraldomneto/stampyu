package com.starksoftware.library.security.model.business;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.jboss.logging.Logger;

import com.starksoftware.library.security.model.entity.PasswordHashedAndSalt;

public class Kryptonite {

	private static final int ITERATIONS_COUNT = 1024;
	private static final int HASH_BYTE_SIZE = 128;
	// private static final int SALT_BYTE_SIZE = 16;

	private static Logger LOG = Logger.getLogger(Kryptonite.class);

	public static void main(String[] args) {

		PasswordHashedAndSalt ps = Kryptonite.hashPassword("teste123456");
		boolean c = Kryptonite.passwordMatch("teste123456", ps.getSalt(), ps.getPasswordHashed());
		LOG.info("Finalizado: " + c);
	}

	public static PasswordHashedAndSalt hashPassword(final String plainTextPassword) {

		try {

			// SecureRandom rng = new SecureRandom();
			byte salt[] = "FramewOrk2016".getBytes();
			// rng.nextBytes(salt);

			KeySpec spec = new PBEKeySpec(plainTextPassword.toCharArray(), salt, ITERATIONS_COUNT, HASH_BYTE_SIZE);
			SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			byte[] hash = f.generateSecret(spec).getEncoded();
			Base64.Encoder enc = Base64.getEncoder();

			return new PasswordHashedAndSalt(enc.encodeToString(hash), enc.encodeToString(salt));

		} catch (NoSuchAlgorithmException e) {
			LOG.error("Algoritmo de criptografia não reconhecido");
		} catch (InvalidKeySpecException e) {
			LOG.error("Chave inválida");
		}

		return null;
	}

	public static boolean passwordMatch(final String plainTextPassword, final String storedSalt,
			final String storedPassword) {
		try {
			// Base64.Decoder dec = Base64.getDecoder();
			byte salt[] = "FramewOrk2016".getBytes();

			KeySpec spec = new PBEKeySpec(plainTextPassword.toCharArray(), salt, ITERATIONS_COUNT, HASH_BYTE_SIZE);
			SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			byte[] hash = f.generateSecret(spec).getEncoded();
			Base64.Encoder enc = Base64.getEncoder();

			String passwordHash = enc.encodeToString(hash);

			return storedPassword.equals(passwordHash);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Algoritmo de criptografia não reconhecido");
		} catch (InvalidKeySpecException e) {
			LOG.error("Chave inválida");
		}

		return false;
	}
}
