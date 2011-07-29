package com.pstehlik.pdfRat

/**
 * @author Philip Stehlik
 * @since 0.1
 */

import com.bleedingwolf.ratpack.*


class AppRunner extends RatpackRunner {
  public static final appScript = "src/main/groovy/com/pstehlik/pdfRat/App.groovy"

  public static void main(String[] args){
    new AppRunner().run()
  }

  void run() {
    super.run(new File(appScript))
  }
}

