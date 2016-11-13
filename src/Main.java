public class Main {

    public static void main(String[] args) {

        MrzGenerator mrzGenerator = new MrzGenerator();

        mrzGenerator.setType("P");
        mrzGenerator.setFirstName("Mahbod");
        mrzGenerator.setMiddleName(null);
        mrzGenerator.setLastName("Khosravani");
        mrzGenerator.setIssuingStateCode("IRN");
        mrzGenerator.setNationalityCode("IRN");
        mrzGenerator.setDateOfBirth("900218");
        mrzGenerator.setExpiryDate("210218");
        mrzGenerator.setSexCode("M");
        mrzGenerator.setCountryCode("IRN");
        mrzGenerator.setPassportNo("S00020023");
        mrzGenerator.setExtraInfo(null);

        System.out.println(mrzGenerator.getFirstLine());
        System.out.println(mrzGenerator.getSecondLine());

    }
}
