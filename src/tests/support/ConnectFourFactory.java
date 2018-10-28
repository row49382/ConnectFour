package tests.support;

import application.models.ConnectFour;
import tests.enums.ConnectFourGameboardState;

/**
 * Factory interface
 * @author Robert Wroblewski
 *
 */
public interface ConnectFourFactory 
{	
	public ConnectFour getConnectFourEntity(ConnectFourGameboardState gameBoardType) throws Exception;
}
