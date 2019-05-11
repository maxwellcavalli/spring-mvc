import java.io.Serializable;
import java.math.BigDecimal;

public  class CalculatedBrazilianTax  implements Serializable
{

 	/** Default serialVersionUID value. */
 
 	private static final long serialVersionUID = 1L;

	/** <i>Generated property</i> for <code>CalculatedBrazilianTax.productPrice</code> property defined at extension <code>fhbraziliantaxes</code>. */
		
	private BigDecimal productPrice;

	/** <i>Generated property</i> for <code>CalculatedBrazilianTax.calculatedIcmsValue</code> property defined at extension <code>fhbraziliantaxes</code>. */
		
	private BigDecimal calculatedIcmsValue;

	/** <i>Generated property</i> for <code>CalculatedBrazilianTax.calculatedPisValue</code> property defined at extension <code>fhbraziliantaxes</code>. */
		
	private BigDecimal calculatedPisValue;

	/** <i>Generated property</i> for <code>CalculatedBrazilianTax.calculatedCofinsValue</code> property defined at extension <code>fhbraziliantaxes</code>. */
		
	private BigDecimal calculatedCofinsValue;

	/** <i>Generated property</i> for <code>CalculatedBrazilianTax.priceWithTaxesAndIPI</code> property defined at extension <code>fhbraziliantaxes</code>. */
		
	private BigDecimal priceWithTaxesAndIPI;

	/** <i>Generated property</i> for <code>CalculatedBrazilianTax.calculatedIPIValue</code> property defined at extension <code>fhbraziliantaxes</code>. */
		
	private BigDecimal calculatedIPIValue;

	/** <i>Generated property</i> for <code>CalculatedBrazilianTax.icmsFactor</code> property defined at extension <code>fhbraziliantaxes</code>. */
		
	private BigDecimal icmsFactor;

	/** <i>Generated property</i> for <code>CalculatedBrazilianTax.ipiFactor</code> property defined at extension <code>fhbraziliantaxes</code>. */
		
	private BigDecimal ipiFactor;

	/** <i>Generated property</i> for <code>CalculatedBrazilianTax.pisFactor</code> property defined at extension <code>fhbraziliantaxes</code>. */
		
	private BigDecimal pisFactor;

	/** <i>Generated property</i> for <code>CalculatedBrazilianTax.cofinsFactor</code> property defined at extension <code>fhbraziliantaxes</code>. */
		
	private BigDecimal cofinsFactor;

	/** <i>Generated property</i> for <code>CalculatedBrazilianTax.newUnitPrice</code> property defined at extension <code>fhbraziliantaxes</code>. */
		
	private BigDecimal newUnitPrice;

	private BigDecimal totalTaxes;

	private BigDecimal originalProductPriceUnit;

	private BigDecimal newTotalPrice;

	private Long quantity;


	public CalculatedBrazilianTax()
	{
		// default constructor
	}
	
		
	
	public void setProductPrice(final BigDecimal productPrice)
	{
		this.productPrice = productPrice;
	}

		
	
	public BigDecimal getProductPrice() 
	{
		return productPrice;
	}
	
		
	
	public void setCalculatedIcmsValue(final BigDecimal calculatedIcmsValue)
	{
		this.calculatedIcmsValue = calculatedIcmsValue;
	}

		
	
	public BigDecimal getCalculatedIcmsValue() 
	{
		return calculatedIcmsValue;
	}
	
		
	
	public void setCalculatedPisValue(final BigDecimal calculatedPisValue)
	{
		this.calculatedPisValue = calculatedPisValue;
	}

		
	
	public BigDecimal getCalculatedPisValue() 
	{
		return calculatedPisValue;
	}
	
		
	
	public void setCalculatedCofinsValue(final BigDecimal calculatedCofinsValue)
	{
		this.calculatedCofinsValue = calculatedCofinsValue;
	}

		
	
	public BigDecimal getCalculatedCofinsValue() 
	{
		return calculatedCofinsValue;
	}
	
		
	
	public void setPriceWithTaxesAndIPI(final BigDecimal priceWithTaxesAndIPI)
	{
		this.priceWithTaxesAndIPI = priceWithTaxesAndIPI;
	}

		
	
	public BigDecimal getPriceWithTaxesAndIPI() 
	{
		return priceWithTaxesAndIPI;
	}
	
		
	
	public void setCalculatedIPIValue(final BigDecimal calculatedIPIValue)
	{
		this.calculatedIPIValue = calculatedIPIValue;
	}

		
	
	public BigDecimal getCalculatedIPIValue() 
	{
		return calculatedIPIValue;
	}
	
		
	
	public void setIcmsFactor(final BigDecimal icmsFactor)
	{
		this.icmsFactor = icmsFactor;
	}

		
	
	public BigDecimal getIcmsFactor() 
	{
		return icmsFactor;
	}
	
		
	
	public void setIpiFactor(final BigDecimal ipiFactor)
	{
		this.ipiFactor = ipiFactor;
	}

		
	
	public BigDecimal getIpiFactor() 
	{
		return ipiFactor;
	}
	
		
	
	public void setPisFactor(final BigDecimal pisFactor)
	{
		this.pisFactor = pisFactor;
	}

		
	
	public BigDecimal getPisFactor() 
	{
		return pisFactor;
	}
	
		
	
	public void setCofinsFactor(final BigDecimal cofinsFactor)
	{
		this.cofinsFactor = cofinsFactor;
	}

		
	
	public BigDecimal getCofinsFactor() 
	{
		return cofinsFactor;
	}
	
		
	
	public void setNewUnitPrice(final BigDecimal newUnitPrice)
	{
		this.newUnitPrice = newUnitPrice;
	}

		
	
	public BigDecimal getNewUnitPrice() 
	{
		return newUnitPrice;
	}


	public BigDecimal getTotalTaxes() {
		return totalTaxes;
	}

	public void setTotalTaxes(BigDecimal totalTaxes) {
		this.totalTaxes = totalTaxes;
	}

	public BigDecimal getOriginalProductPriceUnit() {
		return originalProductPriceUnit;
	}

	public void setOriginalProductPriceUnit(BigDecimal originalProductPriceUnit) {
		this.originalProductPriceUnit = originalProductPriceUnit;
	}

	public BigDecimal getNewTotalPrice() {
		return newTotalPrice;
	}

	public void setNewTotalPrice(BigDecimal newTotalPrice) {
		this.newTotalPrice = newTotalPrice;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
}
