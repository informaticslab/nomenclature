/**
 * 
 */
package gov.cdc.irdu.athena.shared;

/**
 *
 * @author Joel M. Rives
 * Mar 23, 2011
 */
public class SimpleLoincDTO extends BaseDTO {

	private static final long serialVersionUID = -8550574513933551849L;

	private String classType;
	private String className;
	private String code;
	private String shortName;
	private String longName;
	
	public SimpleLoincDTO() {
	}
	
	public SimpleLoincDTO(Long id, String classType, String className, String code, String shortName, String longName) {
		super(id);
		this.classType = classType;
		this.className = className;
		this.code = code;
		this.shortName = shortName;
		this.longName = longName;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}
	
}
