// TOTPUtil.java
package com.cookandroid.mobile_project.util;

import android.annotation.SuppressLint;

import org.apache.commons.codec.binary.Base32;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import dev.samstevens.totp.code.DefaultCodeGenerator;

public class TOTPUtil {
    // TOTP 생성 메서드1: 라이브러리를 사용하는 방식
    public static String getCurrentTOTP(String secret) {
        // default algorithm (HMAC-SHA1) 사용하는 TOTP 코드 생성기 초기화
        DefaultCodeGenerator generator = new DefaultCodeGenerator();

        // 30초마다 코드를 생성
        long timeIndex = System.currentTimeMillis() / 1000 / 30;
        try {
            // TOTP 생성
            return generator.generate(secret, timeIndex);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // TOTP 생성 메서드2: 수동 구현 방식
    @SuppressLint("DefaultLocale")
    public static String getCurrentTOTP2(String base32Secret){
        Base32 base32 = new Base32();
        byte[] key = base32.decode(base32Secret); // Base32 형태인 비밀 키를 바이트 형태로 변환
        long timeIndex = System.currentTimeMillis() / 1000/ 30; // 30초마다 시간을 측정
        byte[] data = ByteBuffer.allocate(8).putLong(timeIndex).array(); // timeIndex를 8바이트 배열로 변환

        try{
            Mac hmac = Mac.getInstance("HmacSHA1"); // HMAC 알고리즘 객체 생성
            SecretKeySpec keySpec = new SecretKeySpec(key, "HmacSHA1"); // 비밀키를 SecretKeySpec 형태로 래핑하여 HMAC에 전달 가능한 형태로 바꿈
            hmac.init(keySpec); // hmac 객체에 비밀키 설정

            byte[] hmacResult = hmac.doFinal(data); // 시간 데이터를 넣어 HMAC 해시를 계산

            /*
               1. 배열의 마지막 바이트의 하위 4비트값을 0x0f와 and하여 오프셋으로 사용
               2. 오프셋부터 4바이트를 조합
               3. 첫 바이트는 0x7f와 and하여 부호 제거 (첫자리를 0으로 만들어 양수 유지)
               4. 나머지는 0xff와 and하여 정수로 변환
            */
            int offset = hmacResult[hmacResult.length - 1] & 0x0F;
            int binary = ((hmacResult[offset] & 0x7f) << 24) |
                            ((hmacResult[offset+1] & 0xff) << 16) | // 0xff : 부호 제거/안전한 정수로 변환 역할
                            ((hmacResult[offset+2] & 0xff) << 8) |
                            (hmacResult[offset+3] & 0xff);

            int otp = binary % 1_000_000; // 6자리
            return String.format("%06d", otp); // String 으로 반환
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
