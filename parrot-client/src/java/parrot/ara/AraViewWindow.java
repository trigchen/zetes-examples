package parrot.ara;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.mihalis.opal.obutton.OButton;

import parrot.client.APIClient;
import parrot.client.ClientConnectionProblemException;
import parrot.client.Session;
import parrot.client.data.objects.Message;
import zetes.wings.base.ViewWindowBase;

public class AraViewWindow extends ViewWindowBase<AraDocument> {
	private APIClient apiClient;
	private Session session;

	private InputComposite text;
	private ScrolledComposite messagesScrolledComposite;
	private Composite messagesListComposite;

	private Color light, dark;

	private PaintOverListener messagesListComposite_shadowPaintListener;	
	private PaintOverListener inputPanelComposite_lightDarkGradientListener;
	
	private SelectionAdapter sendSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			try {
				session.sendMessage(text.getText());
				text.setText("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private SelectionAdapter updateSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			try {
				Message[] newMessages = session.getLatestMessages();
				for (int i = 0; i < newMessages.length; i++) {

					ArrayList<PaintOverListener> pols = new ArrayList<PaintOverListener>();
					pols.add(messagesListComposite_shadowPaintListener);
					
					MessageView newMessageView = new MessageView(messagesListComposite, pols, SWT.NONE);
					newMessageView.setLayoutData(new RowData(messagesScrolledComposite.getSize().x, SWT.DEFAULT));
					newMessageView.setMessage(newMessages[i]);
					//newMessageView.addListener(SWT.Paint, inputComposite_lightDarkGradientListener);
					//newMessageView.addListener(SWT.Paint, messagesListComposite_shadowPaintListener);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private void openSession() {
		try {
			session = new Session(apiClient, "il", "1234");
		} catch (ClientConnectionProblemException e) {
			e.printStackTrace();
		}
	}

	public AraViewWindow(APIClient apiClient) {
		this.apiClient = apiClient;
		openSession();
	}

	/**
	 * Create contents of the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	@Override
	protected Shell constructShell() {
		final Display display = Display.getCurrent();
		Color backColor = display.getSystemColor(
				SWT.COLOR_LIST_BACKGROUND);

		final Shell shell = new Shell(SWT.TITLE | SWT.CLOSE | SWT.MIN | SWT.MAX
				| SWT.RESIZE | SWT.BORDER | SWT.DOUBLE_BUFFERED);
		shell.setSize(401, 358);

		shell.setMinimumSize(new Point(250, 200));

		shell.setImages(new Image[] {
				SWTResourceManager.getImage(AraViewWindow.class,
						"/flyer/flyer512.png"), // Necessary in OS X
				SWTResourceManager.getImage(AraViewWindow.class,
						"/flyer/flyer64.png"), // Necessary in Windows (for
												// Alt-Tab)
				SWTResourceManager.getImage(AraViewWindow.class,
						"/flyer/flyer16.png") // Necessary in Windows (for
												// taskbar)
		});
		shell.setBackground(backColor);
		GridLayout gl_shell = new GridLayout(1, false);
		gl_shell.verticalSpacing = 0;
		gl_shell.marginWidth = 0;
		gl_shell.marginHeight = 0;
		gl_shell.horizontalSpacing = 0;
		shell.setLayout(gl_shell);
		
		messagesScrolledComposite = new ScrolledComposite(shell, SWT.V_SCROLL);
		messagesScrolledComposite.setExpandHorizontal(true);
		GridData gd_messagesScrolledComposite = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_messagesScrolledComposite.heightHint = 205;
		messagesScrolledComposite.setLayoutData(gd_messagesScrolledComposite);
		messagesScrolledComposite.setExpandVertical(true);
		
		messagesListComposite = new Composite(messagesScrolledComposite, SWT.NONE);
		RowLayout rl_messagesListComposite = new RowLayout(SWT.VERTICAL);
		rl_messagesListComposite.wrap = false;
		rl_messagesListComposite.pack = false;
		rl_messagesListComposite.center = true;
		messagesListComposite.setLayout(rl_messagesListComposite);
		messagesScrolledComposite.setContent(messagesListComposite);
		messagesScrolledComposite.setMinSize(messagesListComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		final Composite inputPanelComposite = new Composite(shell, SWT.NONE);
		inputPanelComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_inputPanelComposite = new GridLayout(2, false);
		gl_inputPanelComposite.marginBottom = 5;
		gl_inputPanelComposite.marginLeft = 5;
		gl_inputPanelComposite.marginTop = 5;
		gl_inputPanelComposite.marginWidth = 0;
		gl_inputPanelComposite.marginRight = 5;
		gl_inputPanelComposite.marginHeight = 0;
		inputPanelComposite.setLayout(gl_inputPanelComposite);
		
		Color base = display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		
		light = new Color(display, 
				(int)Math.min(base.getRed() * 1.1, 255),
				(int)Math.min(base.getGreen() * 1.1, 255),
				(int)Math.min(base.getBlue() * 1.1, 255));
				
		dark = new Color(display, 
				(int)(base.getRed() * 0.9),
				(int)(base.getGreen() * 0.9),
				(int)(base.getBlue() * 0.9));

		messagesListComposite_shadowPaintListener = new PaintOverListener(messagesListComposite) {
			@Override
			public void handleEvent(Event event, Rectangle thisRect) {
				GC gc = event.gc;
				gc.setAdvanced(true);

				// Shadow
				int shadowHeight = 20;
				for (int y = 0; y < shadowHeight; y++) {
					gc.setAlpha((int)(128 * Math.pow((double)y / shadowHeight, 5)));
					gc.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_BLACK));
					gc.fillRectangle(thisRect.x, thisRect.y + thisRect.height - shadowHeight + y, thisRect.width, 1);
				}
				
				gc.dispose();
			}
		};
		messagesListComposite.addListener(SWT.Paint, messagesListComposite_shadowPaintListener);

		inputPanelComposite_lightDarkGradientListener = new PaintOverListener(inputPanelComposite) {

				@Override
				public void handleEvent(Event event, Rectangle thisRect) {
					GC gc = event.gc;

					gc.setForeground(light);
					gc.setBackground(dark);
					gc.fillGradientRectangle(thisRect.x, thisRect.y, thisRect.width, thisRect.height, true);
				}
		};
		inputPanelComposite.addListener(SWT.Paint, inputPanelComposite_lightDarkGradientListener);
		
		text = new InputComposite(inputPanelComposite, SWT.BORDER | SWT.DOUBLE_BUFFERED);

		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));
		
		OButton sendButton = new OButton(inputPanelComposite, SWT.NONE);
		GridData gd_button = new GridData(SWT.CENTER, SWT.CENTER, false, false,	1, 1);
		gd_button.widthHint = 109;
		sendButton.setLayoutData(gd_button);
		sendButton.setText("Send");
		GridLayout gl_button = new GridLayout(1, false);
		gl_button.marginHeight = 1;
		gl_button.horizontalSpacing = 0;
		sendButton.setLayout(gl_button);
		sendButton.addSelectionListener(sendSelectionAdapter);

		Button updateButton = new Button(inputPanelComposite, SWT.NONE);
		updateButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		updateButton.addSelectionListener(updateSelectionAdapter);
		updateButton.setText("Update");

		shell.addListener(SWT.Resize, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				shell.layout();
			}
		});
		
		shell.addListener(SWT.Dispose, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				light.dispose();
				dark.dispose();
			}
		});
		
		return shell;
	}

	@Override
	public void setDocument(AraDocument document) {
		super.setDocument(document);
		getShell().forceActive();
	}

	@Override
	public boolean supportsFullscreen() {
		return true;
	}

	@Override
	public boolean supportsMaximizing() {
		return true;
	}
	
}
