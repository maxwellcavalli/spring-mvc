import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Main {

    static int NUM_CALCULATION_DIGITS = 4;

    public static void main(String[] args) {


        //Long quantity = 100l;

        BigDecimal productPriceWithFactor = BigDecimal.valueOf(1142.55);
        Long factor = 1000l;


        BigDecimal productPrice = productPriceWithFactor.divide(BigDecimal.valueOf(factor), 9, RoundingMode.HALF_UP);
        //productPrice = productPrice.multiply(BigDecimal.valueOf(quantity));


        BigDecimal ipiTaxValue = BigDecimal.valueOf(20);
        BigDecimal icmsTaxValue = BigDecimal.valueOf(12);
        BigDecimal pisTaxValue = BigDecimal.valueOf(1.65);
        BigDecimal cofinsTaxValue = BigDecimal.valueOf(7.6);


        printLogHeader(true);

        CalculatedBrazilianTax calculatedBrazilianTax = calculateTaxes(ipiTaxValue, icmsTaxValue, pisTaxValue, cofinsTaxValue, productPrice, 1l);
        printLog(calculatedBrazilianTax, true);

        calculatedBrazilianTax = calculateTaxes(ipiTaxValue, icmsTaxValue, pisTaxValue, cofinsTaxValue, productPrice, 10l);
        printLog(calculatedBrazilianTax, true);

        calculatedBrazilianTax = calculateTaxes(ipiTaxValue, icmsTaxValue, pisTaxValue, cofinsTaxValue, productPrice, 100l);
        printLog(calculatedBrazilianTax, true);

        calculatedBrazilianTax = calculateTaxes(ipiTaxValue, icmsTaxValue, pisTaxValue, cofinsTaxValue, productPrice, 1_000l);
        printLog(calculatedBrazilianTax, true);

        calculatedBrazilianTax = calculateTaxes(ipiTaxValue, icmsTaxValue, pisTaxValue, cofinsTaxValue, productPrice, 2_000l);
        printLog(calculatedBrazilianTax, true);

        calculatedBrazilianTax = calculateTaxes(ipiTaxValue, icmsTaxValue, pisTaxValue, cofinsTaxValue, productPrice, 10_000l);
        printLog(calculatedBrazilianTax, true);

    }

    private static CalculatedBrazilianTax calculateTaxes(final BigDecimal ipiTaxValue, final BigDecimal icmsTaxValue,
                                                         final BigDecimal pisTaxValue, final BigDecimal cofinsTaxValue,
                                                         final BigDecimal productPriceP, final Long quantity) {

        BigDecimal productPrice = productPriceP.multiply(BigDecimal.valueOf(quantity));

        BigDecimal bCem = BigDecimal.valueOf(100.00);
        BigDecimal bUm = BigDecimal.ONE;

        BigDecimal ipiCalc = ipiTaxValue.divide(bCem, NUM_CALCULATION_DIGITS, RoundingMode.HALF_UP);
        BigDecimal icmsCalc = icmsTaxValue.divide(bCem, NUM_CALCULATION_DIGITS, RoundingMode.HALF_UP);
        BigDecimal pisCalc = pisTaxValue.divide(bCem, NUM_CALCULATION_DIGITS, RoundingMode.HALF_UP);
        BigDecimal cofinsCalc = cofinsTaxValue.divide(bCem, NUM_CALCULATION_DIGITS, RoundingMode.HALF_UP);

        BigDecimal icmsFactor = bCem
                .multiply(bUm.add(ipiCalc))
                .multiply(icmsCalc);

        BigDecimal tax1 = icmsFactor
                .add(pisTaxValue)
                .add(cofinsTaxValue);

        BigDecimal fator1 = bUm.subtract(tax1.divide(bCem, NUM_CALCULATION_DIGITS, RoundingMode.HALF_UP));
        BigDecimal priceTax = productPrice.divide(fator1, NUM_CALCULATION_DIGITS, RoundingMode.HALF_UP);

        BigDecimal unitPrice = priceTax.divide(BigDecimal.valueOf(quantity), NUM_CALCULATION_DIGITS, RoundingMode.HALF_UP);
        BigDecimal ipiBase = unitPrice.multiply(BigDecimal.valueOf(quantity));

        BigDecimal calculatedIPIValue = ipiBase.multiply(ipiCalc);
        BigDecimal priceWithTaxesAndIPI = ipiBase.add(calculatedIPIValue);

        BigDecimal calculatedIcmsValue = priceWithTaxesAndIPI.multiply(icmsFactor.divide(bCem, NUM_CALCULATION_DIGITS, RoundingMode.HALF_UP));
        BigDecimal calculatedPisValue = priceTax.multiply(pisCalc);
        BigDecimal calculatedCofinsValue = priceTax.multiply(cofinsCalc);

        CalculatedBrazilianTax calculatedBrazilianTax = new CalculatedBrazilianTax();
        calculatedBrazilianTax.setCalculatedIcmsValue(round(calculatedIcmsValue, 2));
        calculatedBrazilianTax.setCalculatedPisValue(round(calculatedPisValue, 2));
        calculatedBrazilianTax.setCalculatedCofinsValue(round(calculatedCofinsValue, 2));
        calculatedBrazilianTax.setPriceWithTaxesAndIPI(round(priceWithTaxesAndIPI, 4));
        calculatedBrazilianTax.setCalculatedIPIValue(round(calculatedIPIValue, 2));
        calculatedBrazilianTax.setProductPrice(round(productPrice, 2));

        calculatedBrazilianTax.setIcmsFactor(icmsFactor);
        calculatedBrazilianTax.setPisFactor(pisTaxValue);
        calculatedBrazilianTax.setCofinsFactor(cofinsTaxValue);
        calculatedBrazilianTax.setIpiFactor(ipiTaxValue);

        calculatedBrazilianTax.setQuantity(quantity);

        BigDecimal totalTaxes = calculatedBrazilianTax.getCalculatedIcmsValue()
                .add(calculatedBrazilianTax.getCalculatedPisValue())
                .add(calculatedBrazilianTax.getCalculatedCofinsValue())
                .add(calculatedBrazilianTax.getCalculatedIPIValue());

        calculatedBrazilianTax.setTotalTaxes(totalTaxes);

        calculateUnitPrice(calculatedBrazilianTax, quantity);

        BigDecimal unitProductPrice = productPrice.divide(BigDecimal.valueOf(quantity), 9, RoundingMode.HALF_UP);
        BigDecimal newTotalPrice = calculatedBrazilianTax.getNewUnitPrice()
                .multiply(BigDecimal.valueOf(quantity))
                .add(calculatedBrazilianTax.getTotalTaxes());

        calculatedBrazilianTax.setOriginalProductPriceUnit(unitProductPrice);
        calculatedBrazilianTax.setNewTotalPrice(newTotalPrice);

        return calculatedBrazilianTax;
    }

    private static String addSpaces(String value, int length) {
        while (value.length() < length) {
            value = value + " ";
        }

        return value;
    }

    private static void printLogHeader(final boolean printTaxes) {
        System.out.print(addSpaces(" Quantity  ", 20) + ";");
        System.out.print(addSpaces(" PriceWithTaxesAndIPI ", 25) + ";");
        System.out.print(addSpaces(" UnitProductPrice ", 20) + ";");
        System.out.print(addSpaces(" NewUnitPrice ", 20) + ";");
        System.out.print(addSpaces(" NewTotalPrice ", 20) + ";");


        if (printTaxes) {
            System.out.print(addSpaces(" ICMS ", 20) + ";");
            System.out.print(addSpaces(" IPI ", 20) + ";");
            System.out.print(addSpaces(" PIS ", 20) + ";");
            System.out.print(addSpaces(" COFINS ", 20) + ";");


            System.out.print(addSpaces(" % ICMS ", 20) + ";");
            System.out.print(addSpaces(" % IPI ", 20) + ";");
            System.out.print(addSpaces(" % PIS ", 20) + ";");
            System.out.print(addSpaces(" % COFINS ", 20) + ";");

        }

        System.out.println("");
    }

    private static void printLog(final CalculatedBrazilianTax calculatedBrazilianTax, final boolean printTaxes) {
        System.out.print(addSpaces(" " + calculatedBrazilianTax.getQuantity(), 20) + ";");
        System.out.print(addSpaces(" " + formatValue(calculatedBrazilianTax.getPriceWithTaxesAndIPI()), 25) + ";");
        System.out.print(addSpaces(" " + formatValue(calculatedBrazilianTax.getOriginalProductPriceUnit()), 20) + ";");
        System.out.print(addSpaces(" " + formatValue(calculatedBrazilianTax.getNewUnitPrice()), 20) + ";");
        System.out.print(addSpaces(" " + formatValue(calculatedBrazilianTax.getNewTotalPrice()), 20) + ";");

        if (printTaxes) {
            System.out.print(addSpaces(" " + formatValue(calculatedBrazilianTax.getCalculatedIcmsValue()), 20) + ";");
            System.out.print(addSpaces(" " + formatValue(calculatedBrazilianTax.getCalculatedIPIValue()), 20) + ";");
            System.out.print(addSpaces(" " + formatValue(calculatedBrazilianTax.getCalculatedPisValue()), 20) + ";");
            System.out.print(addSpaces(" " + formatValue(calculatedBrazilianTax.getCalculatedCofinsValue()), 20) + ";");

            System.out.print(addSpaces(" " + formatValue(calculatedBrazilianTax.getIcmsFactor()), 20) + ";");
            System.out.print(addSpaces(" " + formatValue(calculatedBrazilianTax.getIpiFactor()), 20) + ";");
            System.out.print(addSpaces(" " + formatValue(calculatedBrazilianTax.getPisFactor()), 20) + ";");
            System.out.print(addSpaces(" " + formatValue(calculatedBrazilianTax.getCofinsFactor()), 20) + ";");
        }

        System.out.println("");
    }

    private static String formatValue(BigDecimal value) {
        DecimalFormat df = new DecimalFormat("#0.000000");
        return df.format(value);
    }

    private static void calculateUnitPrice(final CalculatedBrazilianTax calculatedBrazilianTax,
                                           final Long quantity) {

        BigDecimal productPriceWithoutTaxes = calculatedBrazilianTax.getPriceWithTaxesAndIPI()
                .subtract(calculatedBrazilianTax.getTotalTaxes());

        BigDecimal roundedPriceWithTaxesAndIPI = round(calculatedBrazilianTax.getPriceWithTaxesAndIPI(), 4);
        BigDecimal uProductPrice = productPriceWithoutTaxes.divide(BigDecimal.valueOf(quantity), 10, RoundingMode.DOWN);

        roundProblemWorkAround(uProductPrice, quantity, calculatedBrazilianTax.getTotalTaxes(), roundedPriceWithTaxesAndIPI);

        calculatedBrazilianTax.setNewUnitPrice(uProductPrice);
    }

    private static void roundProblemWorkAround(BigDecimal uProductPrice,
                                               final Long quantity,
                                               final BigDecimal totalTaxes,
                                               final BigDecimal valueToCompare) {

        uProductPrice = uProductPrice.subtract(BigDecimal.valueOf(0.0001));

        int i = 0;
        while (i < 10000) {
            uProductPrice = uProductPrice.add(BigDecimal.valueOf(0.000001));
            BigDecimal valueWithTaxes = uProductPrice.multiply(BigDecimal.valueOf(quantity)).add(totalTaxes);
            valueWithTaxes = round(valueWithTaxes, 4);

            if (valueWithTaxes.equals(valueToCompare)) {
                break;
            }

            i++;
        }
    }

    private static BigDecimal round(BigDecimal value, int digits) {
        return value.setScale(digits, RoundingMode.HALF_UP);
    }

    private static BigDecimal round(BigDecimal value, int digits, RoundingMode roundingMode) {
        return value.setScale(digits, roundingMode);
    }

}
