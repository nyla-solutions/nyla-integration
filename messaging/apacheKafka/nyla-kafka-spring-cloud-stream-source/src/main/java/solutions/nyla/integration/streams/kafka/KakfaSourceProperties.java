package solutions.nyla.integration.streams.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("kakfa")
@Validated
public class KakfaSourceProperties
{
	
	/**
	 * @return the groupId
	 */
	public String getGroupId()
	{
		return groupId;
	}
	/**
	 * @return the bootStrapServersConfig
	 */
	public String getBootStrapServersConfig()
	{
		return bootStrapServersConfig;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}
	/**
	 * @param bootStrapServersConfig the bootStrapServersConfig to set
	 */
	public void setBootStrapServersConfig(String bootStrapServersConfig)
	{
		this.bootStrapServersConfig = bootStrapServersConfig;
	}
	private String groupId;
	private String bootStrapServersConfig;

}
