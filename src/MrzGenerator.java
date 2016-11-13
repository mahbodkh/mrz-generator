/**
 * Created by mahbod on 11/12/16.
 */

public class MrzGenerator {
    private String type;
    //F or M
    private String sexCode;
    private String countryCode;
    private String firstName;
    private String middleName;
    private String lastName;
    // (name maker) firstName + middleName + lastName
    private String name;
    // YY/MM/DD
    private String expiryDate;
    // YY/MM/DD
    private String dateOfBirth;
    private String passportNo;
    private String nationalityCode;
    private String issuingStateCode;
    // Sec line
    private String extraInfo;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSexCode() {
        return sexCode;
    }

    public void setSexCode(String sexCode) {
        this.sexCode = sexCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        String firstName = this.firstName;
        String middleName = this.middleName;
        String lastName = this.lastName;
        if (firstName != null && lastName != null) {
            String master = lastName + "  " + firstName;
            return master;
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public String getIssuingStateCode() {
        return issuingStateCode;
    }

    public void setIssuingStateCode(String issuingStateCode) {
        this.issuingStateCode = issuingStateCode;
    }

    public String getExtraInfo() {
        if (extraInfo == null || extraInfo.equals("")) {
            setExtraInfo("              ");
        }
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }


    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    private String getPassportNumChecksum() {
        return checksumGenerator(getPassportNo(), 9);
    }

    private String getDateBirthChecksum() {
        return checksumGenerator(getDateOfBirth(), 6);
    }

    private String getDateExpiryChecksum() {
        return checksumGenerator(getExpiryDate(), 6);
    }

    private String generate(String text) {
        for (int i = text.length(); i <= 43; i++) {
            text += " ";
        }
        return text.replace(" ", "<").toUpperCase();
    }

    // :)
    private String zeroGenerator(String text) {
        String zero = "";
        for (int i = 0; i < 43 - text.length(); i++) {
            zero += 0;
        }
        return zero;
    }

    private String checksumGenerator(String text, int length) {
        byte[] weights = new byte[]{
                7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1,
                7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1,
                7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1,
                7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1,
        };
        int sum = 0;
        for (int i = 0; i < length; i++) {
            if (text.charAt(i) >= 48 && text.charAt(i) < 58) {
                sum += weights[i] * (text.charAt(i) - 0x30);
            } else if (text.charAt(i) >= 'A' && text.charAt(i) <= 'Z') {
                sum += weights[i] * (text.charAt(i) - 0x37);
            } else if (text.charAt(i) >= 'a' && text.charAt(i) <= 'z') {
                sum += weights[i] * (text.charAt(i) - 32 - 0x37);
            }
        }
        return sum % 10 + "";
    }

    //
    private String getPersonalNumberChecksum() {
        String full = getExtraInfo();
        return checksumGenerator(full, 14);
    }

    //
    private String lastChecksumDigit() {

        String zeroForPersonalNumber = "00000000000000";
        if (getExtraInfo().equals("              ")) {
            setExtraInfo(zeroForPersonalNumber);
        }
        String all = getPassportNo()
                + getPassportNumChecksum()
                + getDateOfBirth()
                + getDateBirthChecksum()
                + getExpiryDate()
                + getDateExpiryChecksum()
                + getExtraInfo();

        return checksumGenerator(all, 37);
    }

    //
    public String getFirstLine() {
        String all = getType() + " " + getIssuingStateCode() + getName() + " ";
        return generate(all);
    }

    public String getSecondLine() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer
                .append(getPassportNo())
                .append(getPassportNumChecksum())
                .append(getNationalityCode())
                .append(getDateOfBirth())
                .append(getDateBirthChecksum())
                .append(getSexCode())
                .append(getExpiryDate())
                .append(getDateExpiryChecksum())
                .append(getExtraInfo())
                .append(getPersonalNumberChecksum())
                .append(lastChecksumDigit());

        return generate(String.valueOf(stringBuffer));
    }
}
