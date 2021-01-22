package exceptions;

@SuppressWarnings("serial")
public class CorruptedFileException extends Exception {
//	/****************************************************************************
//	 * Application 		: 	Kennel
//	 * <p>Description 	: 	An exception for dealing with a corrupted file.</p> 
//	 * <p>Author 		:	RODNEY COCKER <br /></p>
//	 ***************************************************************************/
//	public CorruptedFileException()
//	{
//		super("File is corrupted");
//	}

	public CorruptedFileException(String message) {
		super(message);
	}
}
