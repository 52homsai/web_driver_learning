package googlechrome;


import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeTask
{

	protected final String PROJECT_BASED_PATH = "";// "/Users/swang/Documents/workspace/HelloWorld/";

	/* */
	private static String baseUrl;
	/* */
	private static String basePath;
	/* */
	private static String driverPath;

	private static String platformOs;

	final private String _WEB_PAGE_STATUS_INDICATOR = "page-phantomjs-status";
	final private String _WEB_PAGE_STATUS_INDICATOR_READY_STATE = "ready";
	final private String _ATTRIBUTE_SCREEN_CAPTURE_NAME = "data-screen-capture-name";
	final private String _DEFAULT_CSS_SELECTOR_FOR_CAPTURE = ".screen-capture-required";
	final private int _DEFAULT_BROWSER_WIDTH_FOR_CAPTURE = 1916;
	final private int _DEFAULT_BROWSER_HEIGHT_FOR_CAPTURE = 500;

	public ChromeTask()
	{
		if ( SystemUtils.IS_OS_WINDOWS )
		{
			platformOs = "chromedriver.exe";
		}
		else
		{
			platformOs = "chromedriver_mac";
		}

		this.driverPath = PROJECT_BASED_PATH + "src/drivers/google/" + this.platformOs; // path needs to be in class

		makeDefaultDriverExcutableAutomatically();
	}

	private void makeDefaultDriverExcutableAutomatically()
	{
		try
		{
			Runtime.getRuntime().exec( "chmod u+x " + this.driverPath );
		}
		catch ( IOException e )
		{
			// Make it excutable //Dont worry if it failed
		}
	}

	public void startDriver() throws InterruptedException
	{

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		// options.addArguments( "headless", "remote-debugging-port=9222", "disable-gpu", "privileged" );
		File file = new File( this.driverPath );
		if ( file.exists() )
		{
			System.out.println( "driver is available [" + this.driverPath + "]" );
		}
		System.setProperty( ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, this.driverPath );

		ChromeDriver driver = new ChromeDriver( options );
		try
		{
			driver.get( "http://www.google.com.au" );
			Thread.sleep( 5000 );
			WebElement searchBox = driver.findElement( By.name( "q" ) );
			if ( searchBox != null )
			{
				searchBox.sendKeys( "ChromeDriver" );
				searchBox.submit();
				Thread.sleep( 5000 );
				System.out.println( "Send key finished." );
			}
		}
		catch ( java.lang.Exception e )
		{

		}
		finally
		{
			driver.quit();
		}
	}

	public static void main( String[] args ) throws InterruptedException
	{
		ChromeTask sc = new ChromeTask();
		sc.startDriver();
	}

	/*
	 * public void stopProcessingCheck() throws GeneralException
	 * {
	 * // Created new
	 * if ( ( (PdfSummaryReportPrepearThread) Thread.currentThread() ).isTerminated() )
	 * {
	 * throw new GeneralException( "The process has been terminated." );
	 * }
	 * }
	 * public List<CapturedImage> retrieveImages( String relativeUrl, Integer optWidth, Integer optHeight, String optCssSelector ) throws IOException, InterruptedException, GeneralException
	 * {
	 * Timestamp now = DateTimeUtils.showMeDiffBetweenLastShow( null, "run driver" );
	 * WebDriver driver = startDriver();
	 * now = DateTimeUtils.showMeDiffBetweenLastShow( now, "the driver has started" );
	 * List<CapturedImage> resultList = new ArrayList<>();
	 * if ( Strings.isNullOrEmpty( optCssSelector ) )
	 * {
	 * optCssSelector = _DEFAULT_CSS_SELECTOR_FOR_CAPTURE;
	 * }
	 * try
	 * {
	 * String url = this.baseUrl + "/" + relativeUrl;
	 * url += "&template=true";
	 * if ( optWidth != null && optWidth > 0 && optHeight != null && optHeight > 0 )
	 * {
	 * driver.manage().window().setSize( new Dimension( optWidth, optHeight ) ); // set browser width and height
	 * }
	 * else
	 * {
	 * // driver.manage().window().maximize();
	 * driver.manage().window().setSize( new Dimension( _DEFAULT_BROWSER_WIDTH_FOR_CAPTURE, _DEFAULT_BROWSER_HEIGHT_FOR_CAPTURE ) );
	 * }
	 * try
	 * {
	 * LOG.debug( "get information from [" + url + "]" );
	 * driver.get( url );
	 * resultList = getImages( driver, optCssSelector );
	 * }
	 * catch ( NoSuchElementException e )
	 * {
	 * LOG.debug( "Unable to get the images from [" + url + "]" );
	 * }
	 * }
	 * finally
	 * {
	 * driver.quit();
	 * }
	 * shouldThrowException();
	 * return resultList;
	 * }
	 * private WebElement getStatusIndicator( WebDriver driver, int intervalOfChecking ) throws InterruptedException, GeneralException
	 * {
	 * // Do the sleep here. It won't be ready at the very beginning.
	 * Thread.sleep( intervalOfChecking );
	 * WebElement statusIndicator = driver.findElement( By.id( _WEB_PAGE_STATUS_INDICATOR ) );
	 * if ( statusIndicator == null )
	 * {
	 * throw new GeneralException( "Status indicator is not found" );
	 * }
	 * return statusIndicator;
	 * }
	 * private void waitUntilPageIsReady( WebDriver driver, Integer optIntervalOfChecking, Integer optMaxWaitTimeInMsec ) throws InterruptedException, GeneralException
	 * {
	 * if ( optIntervalOfChecking == null )
	 * {
	 * optIntervalOfChecking = 100;
	 * }
	 * if ( optMaxWaitTimeInMsec == null )
	 * {
	 * optMaxWaitTimeInMsec = 1000 * 60 * 5; // 5 mins
	 * }
	 * WebElement statusIndicator = null;
	 * do
	 * {
	 * shouldThrowException();
	 * statusIndicator = getStatusIndicator( driver, optIntervalOfChecking );
	 * }
	 * while ( !_WEB_PAGE_STATUS_INDICATOR_READY_STATE.equals( statusIndicator.getText() ) );
	 * LOG.debug( "====The page is ready. The capture should start====" );
	 * }
	 * private List<CapturedImage> getImages( WebDriver driver, String cssSelector ) throws InterruptedException, IOException, GeneralException
	 * {
	 * LOG.debug( "found element: " + driver.getCurrentUrl() );
	 * Timestamp now = DateTimeUtils.showMeDiffBetweenLastShow( null, "start with" );
	 * waitUntilPageIsReady( driver, null, null );
	 * now = DateTimeUtils.showMeDiffBetweenLastShow( null, "start processing" );
	 * List<WebElement> elements = driver.findElements( By.cssSelector( cssSelector ) );
	 * List<CapturedImage> elementImages = new ArrayList<>();
	 * elementImages = getScreenshots( driver, elements );
	 * return elementImages;
	 * }
	 * private List<CapturedImage> getScreenshots( final WebDriver d, final List<WebElement> elements ) throws IOException, GeneralException
	 * {
	 * final BufferedImage img;
	 * final byte[] screengrab;
	 * Timestamp now = DateTimeUtils.showMeDiffBetweenLastShow( null, "get screenshot of whole screen" );
	 * screengrab = ( (TakesScreenshot) d ).getScreenshotAs( OutputType.BYTES );
	 * now = DateTimeUtils.showMeDiffBetweenLastShow( now, "get screengrab" );
	 * LOG.debug( "The screenshot of whole screen has been captured." );
	 * img = ImageIO.read( new ByteArrayInputStream( screengrab ) );
	 * List<CapturedImage> capturedImages = new ArrayList<>();
	 * for ( WebElement element : elements )
	 * {
	 * shouldThrowException();
	 * capturedImages.add( getSubImage( img, element ) );
	 * LOG.debug( "The sub image has been splited from the whole screenshot." );
	 * now = DateTimeUtils.showMeDiffBetweenLastShow( now, "get sub img done" );
	 * }
	 * return capturedImages;
	 * }
	 * private CapturedImage getSubImage( final BufferedImage wholeScreenShot, WebElement e ) throws IOException
	 * {
	 * Point topleft = e.getLocation();
	 * Point bottomright = new Point( e.getSize().getWidth(), e.getSize().getHeight() );
	 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 * ImageIO.write( wholeScreenShot.getSubimage( topleft.getX(), topleft.getY(), bottomright.getX(), bottomright.getY() ), "png", baos );
	 * byte[] imageInByte = baos.toByteArray();
	 * baos.close();
	 * String elementName = e.getAttribute( _ATTRIBUTE_SCREEN_CAPTURE_NAME );
	 * if ( Strings.isNullOrEmpty( elementName ) )
	 * {
	 * elementName = e.getAttribute( "id" );
	 * }
	 * if ( Strings.isNullOrEmpty( elementName ) )
	 * {
	 * elementName = e.getAttribute( "class" );
	 * }
	 * CapturedImage o = new CapturedImage( imageInByte, e.getSize().getWidth(), e.getSize().getHeight(), elementName );
	 * return o;
	 * }
	 * public class CapturedImage
	 * {
	 * byte[] bytes = null;
	 * Integer width = null;
	 * Integer height = null;
	 * String name = null;
	 * ByteArrayImageProvider image = null;
	 * public CapturedImage( byte[] bytes, Integer width, Integer height, String name )
	 * {
	 * this.bytes = bytes;
	 * this.width = width;
	 * this.height = height;
	 * this.name = name;
	 * this.image = new ByteArrayImageProvider( this.bytes );
	 * resizeByWidthAndKeepRatio( 700f );
	 * }
	 * private void resizeByWidthAndKeepRatio( float newWidth )
	 * {
	 * float newHeight = this.height.floatValue() * newWidth / this.width.floatValue();
	 * this.image.setSize( newWidth, newHeight );
	 * this.image.setUseImageSize( true );
	 * }
	 * private void rotateImageIfNeeded()
	 * {
	 * if ( this.height.floatValue() <= 1.5f * this.width.floatValue() )
	 * {
	 * return; // Do not require rotation
	 * }
	 * // TODO
	 * }
	 * public byte[] getBytes()
	 * {
	 * return bytes;
	 * }
	 * public void setBytes( byte[] bytes )
	 * {
	 * this.bytes = bytes;
	 * }
	 * public Integer getWidth()
	 * {
	 * return width;
	 * }
	 * public void setWidth( Integer width )
	 * {
	 * this.width = width;
	 * }
	 * public Integer getHeight()
	 * {
	 * return height;
	 * }
	 * public void setHeight( Integer height )
	 * {
	 * this.height = height;
	 * }
	 * public String getName()
	 * {
	 * return name;
	 * }
	 * public void setName( String name )
	 * {
	 * this.name = name;
	 * }
	 * public ByteArrayImageProvider getImage()
	 * {
	 * return image;
	 * }
	 * public void setImage( ByteArrayImageProvider image )
	 * {
	 * this.image = image;
	 * }
	 * }
	 * public Attachment produceSummaryPdf( ServicesCustomAccess service, Long enquiryId, SecUser user ) throws DocGenException, GeneralException
	 * {
	 * Enquiry e = service.getEnquiry( enquiryId );
	 * IRecordIdentifier enquiryIdentifier = e.getRecordIdentifer();
	 * IDocument document = docGen.generateDocument( "summary_template", Format.PDF, enquiryIdentifier );
	 * // document = docGen.concatenatePdfDocuments( "Development Summary", "development_summary_" + enquiryId + ".pdf", attachmentList );
	 * document.setDocumentFileName( "development_summary_" + enquiryId + ".pdf" );
	 * Attachment attachment = this.attachmentUtils.createFileAttachment( enquiryIdentifier, document );
	 * attachment.setIsTemporary( false );
	 * attachment = this.attachmentUtils.saveFileAttachment( user, attachment );
	 * if ( attachment == null )
	 * {
	 * throw new GeneralException( "The saving of the document failed." );
	 * }
	 * return attachment;
	 * }
	 * private void preparePdfSummaryReport( Long enquiryId ) throws IOException, InterruptedException, GeneralException
	 * {
	 * NddWebCaptureUtils capturor = new NddWebCaptureUtils();
	 * capturor.retrieveImages( "requirement-details?id=" + enquiryId, 900, 2000, ".require-screen-capture" );
	 * }
	 * private void shouldThrowException() throws GeneralException
	 * {
	 * // Created new
	 * if ( Thread.currentThread() instanceof PdfSummaryReportPrepearThread && ( (PdfSummaryReportPrepearThread) Thread.currentThread() ).isTerminated() )
	 * {
	 * throw new GeneralException( "The thread has been terminated." );
	 * }
	 * }
	 * public class PdfSummaryReportPrepearRunable implements Runnable
	 * {
	 * private long enquiryId = -1;
	 * private ServicesCustomAccess service = null;
	 * private SecUser user = null;
	 * private NddWebCaptureUtils nddWebCaptureUtils;
	 * public PdfSummaryReportPrepearRunable( ServicesCustomAccess service, long enquiryId, SecUser user, NddWebCaptureUtils nddWebCaptureUtils )
	 * {
	 * this.service = service;
	 * this.enquiryId = enquiryId;
	 * this.user = user;
	 * this.nddWebCaptureUtils = nddWebCaptureUtils;
	 * }
	 * @Override
	 * public void run()
	 * {
	 * try
	 * {
	 * // Remove existing
	 * List<Attachment> existingSummaries = service.getAttachments( new GetServiceParameters().whereClause( "where " + Attachment._COL_TABLE_NAME + " = ? and " + Attachment._COL_RECORD_ID + " = ?" ).prepStmtValues(
	 * Enquiry.TABLE_NAME, enquiryId ) );
	 * for ( Attachment a : existingSummaries )
	 * {
	 * this.service.deleteAttachment( a.getId(), user );
	 * }
	 * Attachment attachment = this.nddWebCaptureUtils.produceSummaryPdf( this.service, this.enquiryId, this.user );
	 * nddWebCaptureUtils.shouldThrowException();
	 * // Created new
	 * if ( ( (PdfSummaryReportPrepearThread) Thread.currentThread() ).isTerminated() )
	 * {
	 * // Ignore this
	 * return;
	 * }
	 * this.service.saveAttachment( attachment, user, -1 );
	 * }
	 * catch ( Exception e )
	 * {
	 * // Don't worry
	 * }
	 * }
	 * }
	 * public class PdfSummaryReportPrepearThread extends Thread
	 * {
	 * private long enquiryId = -1;
	 * private ServicesCustomAccess service = null;
	 * private SecUser user = null;
	 * private boolean isTerminated;
	 * private NddWebCaptureUtils nddWebCaptureUtils;
	 * private PdfSummaryReportPrepearRunable pdfSummaryReportPrepearRunable;
	 * public PdfSummaryReportPrepearThread( ServicesCustomAccess service, long enquiryId, SecUser user, NddWebCaptureUtils nddWebCaptureUtils ) throws GeneralException
	 * {
	 * // Kill all existing preparation
	 * if ( enquiryId <= 0 )
	 * {
	 * throw new GeneralException( "Prepare PDF summary cannot be done" );
	 * }
	 * Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
	 * Thread[] threads = threadSet.toArray( new Thread[threadSet.size()] );
	 * for ( Thread t : threads )
	 * {
	 * if ( t instanceof PdfSummaryReportPrepearThread )
	 * {
	 * if ( !t.interrupted() )
	 * {
	 * // Cancel current running PDF generation
	 * PdfSummaryReportPrepearThread tmp = (PdfSummaryReportPrepearThread) t;
	 * if ( enquiryId == tmp.getEnquiryId() )
	 * {
	 * tmp.setTerminated( true );
	 * }
	 * }
	 * }
	 * }
	 * this.service = service;
	 * this.enquiryId = enquiryId;
	 * this.user = user;
	 * this.isTerminated = false;
	 * this.nddWebCaptureUtils = nddWebCaptureUtils;
	 * this.pdfSummaryReportPrepearRunable = new PdfSummaryReportPrepearRunable( this.service, this.enquiryId, this.user, this.nddWebCaptureUtils );
	 * }
	 * @Override
	 * public void run()
	 * {
	 * this.pdfSummaryReportPrepearRunable.run();
	 * }
	 * public long getEnquiryId()
	 * {
	 * return this.enquiryId;
	 * }
	 * public boolean isTerminated()
	 * {
	 * return isTerminated;
	 * }
	 * public void setTerminated( boolean isTerminated )
	 * {
	 * this.isTerminated = isTerminated;
	 * }
	 * }
	 */
}