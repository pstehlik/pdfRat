package com.pstehlik.pdfRat

import org.xml.sax.SAXParseException
import org.xhtmlrenderer.pdf.ITextRenderer
import javax.activation.MimetypesFileTypeMap

MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap()


get("/") {
  """
Renders a PDF via iText.
Usage: call /pdf either via POST or GET and supply the HTML to be rendered in a parameter called 'html'.
Example: http://localhost:5000/pdf?html=<body>hello%20there</body>
"""
}

get("/pdf") {
  String renderedView = request.getParameterValues('html')?.getAt(0)

  response.setHeader('Content-Type', mimetypesFileTypeMap.getContentType('pdf'))
  ITextRenderer renderer
  try {
    //todo Remove multiple instantiations, this is a workaround to avoid error (bug) thrown when this is called for the first time only
    renderer = new ITextRenderer(26.666666f, 16)
  }
  catch (java.lang.Error er) {
    if (er.toString().contains('Probable fatal error:No fonts found')) {
      println "Anticipated error occured while trying to create new ITextRenderer(). Retrying one more time."
    }
    else {
      throw er
    }
  }
  finally {
    try {
      //render the PDF bytes from the view String. Using default DEFAULT_DOTS_PER_POINT and changing the DEFAULT_DOTS_PER_PIXEL to 16.
      renderer = new ITextRenderer(26.666666f, 16)
      if (renderedView.indexOf('<!DOCTYPE') < 0) {
        renderedView = '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">' + renderedView
      }
      renderer.setDocumentFromString(renderedView)
      renderer.layout()
      ByteArrayOutputStream bos = new ByteArrayOutputStream()
      renderer.createPDF(bos)
      return bos.toByteArray()
    } catch (SAXParseException sex) {
      println "Could not render HTML view to PDF due to a SAXParseException: [${sex.message}]"
      return new ByteArrayOutputStream().toByteArray()
    }
  }

}
