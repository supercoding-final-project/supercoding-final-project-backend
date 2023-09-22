package com.github.supercodingfinalprojectbackend.service;

//@SpringBootTest
//public class TestChargePaymoney {
//
//    @Test
//    @Transactional
//    public void testChargePaymoney() throws InterruptedException {
//
//        final int numberOfThread = 1000;
//        UserAbstractAccount account1 = UserAbstractAccount.of();
//        UserAbstractAccount account2 = UserAbstractAccount.of();
//        CountDownLatch latch = new CountDownLatch(numberOfThread);
//
//        for (int i = 0; i < numberOfThread; i++) {
//            new Thread(()->{
//                try {
//                    System.out.println("paymoney1 = " + account1.chargePaymoney(1L));
////                    System.out.println("paymoney2 = " + account2.chargePaymoneyNoLock(1L));
//                } finally {
//                    latch.countDown();
//                }
//            }).start();
//        }
//        latch.await();
//
//        Assertions.assertEquals(numberOfThread, account1.getPaymoney());
////        Assertions.assertEquals(numberOfThread, account2.getPaymoney());
//
//    }
//}
