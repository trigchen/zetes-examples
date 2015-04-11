package htmlview;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

import zetes.wings.base.ViewWindowBase;
import zetes.wings.litehtml.swt.LiteHTMLView;
import zetes.wings.litehtml.swt.LiteHTMLViewListener;

public class HTMLViewWindow extends ViewWindowBase<HTMLDocument> {

	private LiteHTMLView liteHTMLView;
	private ScrolledComposite scrolledComposite;
	
	private LiteHTMLViewListener htmlViewListener = new LiteHTMLViewListener() {
		
		@Override
		public void setCaption(LiteHTMLView view, String caption) {
			getDocument().setDocumentTitle(caption);
		}
	};
	
	private void refreshContent() {
		Point minPageSize = liteHTMLView.computeSize(scrolledComposite.getClientArea().width, SWT.DEFAULT);
		if (minPageSize.y < scrolledComposite.getClientArea().height) {
			minPageSize.y = scrolledComposite.getClientArea().height;
		}
		liteHTMLView.setSize(minPageSize);
	}
	
	@Override
	public boolean supportsFullscreen() {
		return true;
	}


	@Override
	public boolean supportsMaximizing() {
		return true;
	}

	@Override
	public void setDocument(HTMLDocument document) {
		super.setDocument(document);
		liteHTMLView.setContent(document.getHTMLText(), 
				"html { " +
						"    display: block; " +
						"    position: relative; " +
						"} " +
						
						"head { " +
						"    display: none " +
						"} " +
						
						"meta { " +
						"    display: none " +
						"} " +
						
						"title { " +
						"    display: none " +
						"} " +
						
						"link { " +
						"    display: none " +
						"} " +
						
						"style { " +
						"    display: none " +
						"} " +
						
						"script { " +
						"    display: none " +
						"} " +
						
						"body { " +
						"	display:block;  " +
						"	margin:8px;  " +
						"} " +
						
						"p { " +
						"	display:block;  " +
						"	margin-top:1em;  " +
						"	margin-bottom:1em; " +
						"} " +
						
						"b, strong { " +
						"	display:inline;  " +
						"	font-weight:bold; " +
						"} " +
						
						"i, em { " +
						"	display:inline;  " +
						"	font-style:italic; " +
						"} " +
						
						"center  " +
						"{ " +
						"	text-align:center; " +
						"	display:block; " +
						"} " +
						
						"a:link " +
						"{ " +
						"	text-decoration: underline; " +
						"	color: #00f; " +
						"	cursor: pointer; " +
						"} " +
						
						"h1, h2, h3, h4, h5, h6, div { " +
						"	display:block; " +
						"} " +
						
						"h1 { " +
						"	font-weight:bold;  " +
						"	margin-top:0.67em;  " +
						"	margin-bottom:0.67em;  " +
						"	font-size: 2em; " +
						"} " +
						
						"h2 { " +
						"	font-weight:bold;  " +
						"	margin-top:0.83em;  " +
						"	margin-bottom:0.83em;  " +
						"	font-size: 1.5em; " +
						"} " +
						
						"h3 { " +
						"	font-weight:bold;  " +
						"	margin-top:1em;  " +
						"	margin-bottom:1em;  " +
						"	font-size:1.17em; " +
						"} " +
						
						"h4 { " +
						"	font-weight:bold;  " +
						"	margin-top:1.33em;  " +
						"	margin-bottom:1.33em " +
						"} " +
						
						"h5 { " +
						"	font-weight:bold;  " +
						"	margin-top:1.67em;  " +
						"	margin-bottom:1.67em; " +
						"	font-size:.83em; " +
						"} " +
						
						"h6 { " +
						"	font-weight:bold;  " +
						"	margin-top:2.33em;  " +
						"	margin-bottom:2.33em; " +
						"	font-size:.67em; " +
						"}  " +
						
						"br { " +
						"	display:inline-block; " +
						"} " +
						
						"span { " +
						"	display:inline " +
						"} " +
						
						"img { " +
						"	display: inline-block; " +
						"} " +
						
						"img[align=\"right\"] " +
						"{ " +
						"	float: right; " +
						"} " +
						
						"img[align=\"left\"] " +
						"{ " +
						"	float: left; " +
						"} " +
						
						"hr { " +
						"    display: block; " +
						"    margin-top: 0.5em; " +
						"    margin-bottom: 0.5em; " +
						"    margin-left: auto; " +
						"    margin-right: auto; " +
						"    border-style: inset; " +
						"    border-width: 1px " +
						"} " +
						
						
						"/***************** TABLES ********************/ " +
						
						"table { " +
						"    display: table; " +
						"    border-collapse: separate; " +
						"    border-spacing: 2px; " +
						"    border-top-color:gray; " +
						"    border-left-color:gray; " +
						"    border-bottom-color:black; " +
						"    border-right-color:black; " +
						"} " +
						
						"tbody, tfoot, thead { " +
						"	display:table-row-group; " +
						"	vertical-align:middle; " +
						"} " +
						
						"tr { " +
						"    display: table-row; " +
						"    vertical-align: inherit; " +
						"    border-color: inherit; " +
						"} " +
						
						"td, th { " +
						"    display: table-cell; " +
						"    vertical-align: inherit; " +
						"    border-width:1px; " +
						"    padding:1px; " +
						"} " +
						
						"th { " +
						"	font-weight: bold; " +
						"} " +
						
						"table[border] { " +
						"    border-style:solid; " +
						"} " +
						
						"table[border|=0] { " +
						"    border-style:none; " +
						"} " +
						
						"table[border] td, table[border] th { " +
						"    border-style:solid; " +
						"    border-top-color:black; " +
						"    border-left-color:black; " +
						"    border-bottom-color:gray; " +
						"    border-right-color:gray; " +
						"} " +
						
						"table[border|=0] td, table[border|=0] th { " +
						"    border-style:none; " +
						"} " +
						
						"caption { " +
						"	display: table-caption; " +
						"} " +
						
						"td[nowrap], th[nowrap] { " +
						"	white-space:nowrap; " +
						"} " +
						
						"tt, code, kbd, samp { " +
						"    font-family: monospace " +
						"} " +
						
						"pre, xmp, plaintext, listing { " +
						"    display: block; " +
						"    font-family: monospace; " +
						"    white-space: pre; " +
						"    margin: 1em 0 " +
						"} " +
						
						"/***************** LISTS ********************/ " +
						
						"ul, menu, dir { " +
						"    display: block; " +
						"    list-style-type: disc; " +
						"    margin-top: 1em; " +
						"    margin-bottom: 1em; " +
						"    margin-left: 0; " +
						"    margin-right: 0; " +
						"    padding-left: 40px " +
						"} " +
						
						"ol { " +
						"    display: block; " +
						"    list-style-type: decimal; " +
						"    margin-top: 1em; " +
						"    margin-bottom: 1em; " +
						"    margin-left: 0; " +
						"    margin-right: 0; " +
						"    padding-left: 40px " +
						"} " +
						
						"li { " +
						"    display: list-item; " +
						"} " +
						
						"ul ul, ol ul { " +
						"    list-style-type: circle; " +
						"} " +
						
						"ol ol ul, ol ul ul, ul ol ul, ul ul ul { " +
						"    list-style-type: square; " +
						"} " +
						
						"dd { " +
						"    display: block; " +
						"    margin-left: 40px; " +
						"} " +
						
						"dl { " +
						"    display: block; " +
						"    margin-top: 1em; " +
						"    margin-bottom: 1em; " +
						"    margin-left: 0; " +
						"    margin-right: 0; " +
						"} " +
						
						"dt { " +
						"    display: block; " +
						"} " +
						
						"ol ul, ul ol, ul ul, ol ol { " +
						"    margin-top: 0; " +
						"    margin-bottom: 0 " +
						"} " +
						
						"blockquote { " +
						"	display: block; " +
						"	margin-top: 1em; " +
						"	margin-bottom: 1em; " +
						"	margin-left: 40px; " +
						"	margin-left: 40px; " +
						"} " +
						
						"/*********** FORM ELEMENTS ************/ " +
						
						"form { " +
						"	display: block; " +
						"	margin-top: 0em; " +
						"} " +
						
						"option { " +
						"	display: none; " +
						"} " +
						
						"input, textarea, keygen, select, button, isindex { " +
						"	margin: 0em; " +
						"	color: initial; " +
						"	line-height: normal; " +
						"	text-transform: none; " +
						"	text-indent: 0; " +
						"	text-shadow: none; " +
						"	display: inline-block; " +
						"} " +
						"input[type=\"hidden\"] { " +
						"	display: none; " +
						"} " +
						
						"article, aside, footer, header, hgroup, nav, section  " +
						"{ " +
						"	display: block; " +
						"} ");

		File parentFile = new File(document.getFileName()).getParentFile();
		liteHTMLView.setBasePath(parentFile.getAbsolutePath());
		refreshContent();
	}

	@Override
	protected Shell constructShell() {
		Shell shell = new Shell(SWT.MIN | SWT.MAX | SWT.CLOSE | SWT.RESIZE | SWT.DOUBLE_BUFFERED);
		shell.setSize(500, 350);
		shell.setText("LiteHTML Demo");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		scrolledComposite = new ScrolledComposite(shell, SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		
		liteHTMLView = new LiteHTMLView(scrolledComposite, SWT.DOUBLE_BUFFERED);
		liteHTMLView.setListener(htmlViewListener);
		scrolledComposite.setContent(liteHTMLView);
		
		ControlListener scrollControlListener = new ControlListener() {
			
			@Override
			public void controlResized(ControlEvent arg0) {
				refreshContent();
			}
			
			@Override public void controlMoved(ControlEvent arg0) { }
		};
		
		scrolledComposite.addControlListener(scrollControlListener);
		return shell;
	}
}
