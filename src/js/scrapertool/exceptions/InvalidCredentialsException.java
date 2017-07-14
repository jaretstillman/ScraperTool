package js.scrapertool.exceptions;

/*
 * Description: Exception for Invalid Login Credentials
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */

public class InvalidCredentialsException extends Exception
{
	private static final long serialVersionUID = 1L;
	private String message;
	public InvalidCredentialsException(String string)
	{
		message=string;
	}
	public String getMessage()
	{
		return message;
	}

}
