    /**
     * 사용자 입력 기반 TOTP 코드 검증을 위한 유틸.
     * 현재는 사용되지 않음. 추후 로그인 2단계 인증 기능에서 활용 예정.
     */
    public static boolean verifyCode(String secret, String inputCode) {
        // TOTP 생성기와 시스템 시간 공급자 초기화
        DefaultCodeGenerator codeGenerator = new DefaultCodeGenerator();
        SystemTimeProvider timeProvider = new SystemTimeProvider();

        // 코드 검증기 생성, TOTP 갱신 주기 30초 설정
        DefaultCodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        verifier.setTimePeriod(30);  // 30초 유효시간

        // 현재 시간 기준으로 secret과 inputCode를 비교하여 일치하면 true 반환
        return verifier.isValidCode(secret, inputCode);
    }