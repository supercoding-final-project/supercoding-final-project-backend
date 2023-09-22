package com.github.supercodingfinalprojectbackend.service;

//@SpringBootTest
//@Transactional
//public class TestOauth2Service {
//
//    @Autowired
//    private Oauth2Service oauth2Service;
//    @Autowired
//    private UserSocialInfoRepository userSocialInfoRepository;
//
//    @Test
//    public void testAutoSignUp() throws InterruptedException {
//        final int threadNumber = 4;
//        final long kakaoId = 105L;
//
//        CountDownLatch latch = new CountDownLatch(threadNumber);
//        for (int i = 0; i < threadNumber; i++) {
//            try {
//                new Thread(()->{
//                    try {
//                        oauth2Service.autoSignUp(kakaoId, SocialPlatformType.KAKAO, "이메일", "닉네임", "썸네일 이미지 url");
//                    } catch (DataIntegrityViolationException e) {
//                        System.out.println("중복 회원 가입 발생!중복 회원 가입 발생!중복 회원 가입 발생!중복 회원 가입 발생!중복 회원 가입 발생!");
//                    }
//                }).start();
//            } finally {
//                latch.countDown();
//            }
//        }
//        latch.await();
//
//        Thread.sleep(10_000L);
//        List<UserSocialInfo> result = userSocialInfoRepository.findAllBySocialIdAndSocialPlatformNameAndIsDeletedIsFalse(kakaoId, SocialPlatformType.KAKAO.toString());
//        Assertions.assertEquals(1, result.size());
//    }
//}
