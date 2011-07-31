package com.pstehlik.pdfRat

import com.bleedingwolf.ratpack.RatpackApp

/**
 * Description missing
 *
 * @author Philip Stehlik
 * @since
 */
class AppServlet extends com.bleedingwolf.ratpack.RatpackServlet {

  void init() {
    if (app == null) {
      def appScriptName = servletConfig.getInitParameter("app-script-filename")
      def fullScriptPath = servletContext.getRealPath("WEB-INF/lib/${appScriptName}")
      println "Loading app from script '${fullScriptPath}'"
      app = new RatpackApp()
      app.prepareScriptForExecutionOnApp(fullScriptPath as String)
    }
  }
}
