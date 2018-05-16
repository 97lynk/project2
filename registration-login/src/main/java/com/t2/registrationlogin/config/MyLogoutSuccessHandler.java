/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t2.registrationlogin.config;

/**
 *
 * @author 97lynk
 */
//@Component("myLogoutSuccessHandler")
//public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
//
//    private static final Logger logger
//            = Logger.getLogger(MyLogoutSuccessHandler.class.getName());
//
//    @Override
//    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//        final HttpSession session = request.getSession();
//        if (session != null) {
//            session.removeAttribute("user");
//        }
//        Enumeration<String> ss = session.getAttributeNames();
//        while (ss.hasMoreElements()) {
//            logger.info(ss.nextElement());
//        }
//        super.onLogoutSuccess(request, response, authentication);
//        response.sendRedirect("/u/logout");
//    }
//}
