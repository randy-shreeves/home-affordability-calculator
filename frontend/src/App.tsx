import { useState } from "react";

function App() {

  const [monthlyPayment, setMonthlyPayment] = useState("0");
  const [downPayment, setDownPayment] = useState("0");
  const [interestRate, setInterestRate] = useState("0");
  const [loanTermYears, setLoanTermYears] = useState<number>(30);
  const [hoaMonthlyFees, setHoaMonthlyFees] = useState("0");
  const [propertyTaxRate, setPropertyTaxRate] = useState("0");
  const [homeownersInsuranceRate, setHomeownersInsuranceRate] = useState("0");
  const [pmiRate, setPmiRate] = useState("0");
    const [errorMessage, setErrorMessage] = useState("");

  const [result, setResult] = useState<number>(0);

  async function calculateAffordability() {
      const validationError = validateInputs();
      if (validationError) {
          setErrorMessage(validationError);
          return;
      }
      setErrorMessage("");
      try {
          const response = await fetch(
              "http://localhost:8080/api/affordability/estimate",
              {
                  method: "POST",
                  headers: {
                      "Content-Type": "application/json",
                  },
                  body: JSON.stringify({
                      monthlyPayment: Number(monthlyPayment),
                      downPayment: Number(downPayment),
                      interestRate: Number(interestRate),
                      loanTermYears: Number(loanTermYears),
                      hoaMonthlyFees: Number(hoaMonthlyFees),
                      propertyTaxRate: Number(propertyTaxRate),
                      homeownersInsuranceRate: Number(homeownersInsuranceRate),
                      pmiRate: Number(pmiRate),
                  }),
              }
          );

          const data = await response.json();
          setResult(data.maxHomePrice);
      } catch (error) {
          console.error(error);
          setResult(0);
      }
  }

    function hasMoreThanTwoDecimals(value: string): boolean {
        if (!value.includes(".")) {
            return false;
        }
        const decimalPortion = value.split(".")[1];
        return decimalPortion.length > 2;
    }

    function validateInputs(): string {

        const fields = [
            monthlyPayment,
            downPayment,
            interestRate,
            hoaMonthlyFees,
            propertyTaxRate,
            homeownersInsuranceRate,
            pmiRate
        ];
        for (const field of fields) {
            if (field.trim() === "") {
                return "All fields must contain numeric values.";
            }
            const numericValue = Number(field);
            if (isNaN(numericValue)) {
                return "All fields must be numeric.";
            }
            if (hasMoreThanTwoDecimals(field)) {
                return "Values cannot exceed 2 decimal places.";
            }
            if (numericValue < 0) {
                return "Values cannot be negative.";
            }
        }
        return "";
    }

    return (
        <div style={{ padding: "2rem", fontFamily: "Arial" }}>

            <h1>Home Affordability Estimator</h1>

            {errorMessage && (
                <div
                    style={{
                        color: "red",
                        marginBottom: "20px",
                        fontWeight: "bold"
                    }}
                >
                    {errorMessage}
                </div>
            )}

            <div
                style={{
                    display: "flex",
                    gap: "50px",
                    marginBottom: "20px"
                }}
            >

                {/* Left Column */}
                <div>

                    <div style={{ marginBottom: "10px" }}>
                        <label htmlFor="monthlyPayment">
                            Desired Monthly Payment
                        </label>
                        <br />
                        <input
                            type="number"
                            min="0"
                            step="0.01"
                            value={monthlyPayment}
                            onChange={(e) => setMonthlyPayment(e.target.value)}
                        />
                    </div>

                    <div style={{ marginBottom: "10px" }}>
                        <label htmlFor="downPayment">
                            Down Payment
                        </label>
                        <br />
                        <input
                            type="number"
                            min="0"
                            step="0.01"
                            value={downPayment}
                            onChange={(e) => setDownPayment(e.target.value)}
                        />
                    </div>

                    <div style={{ marginBottom: "10px" }}>
                        <label htmlFor="interestRate">
                            Interest Rate (%)
                        </label>
                        <br />
                        <input
                            type="number"
                            min="0"
                            step="0.01"
                            value={interestRate}
                            onChange={(e) => setInterestRate(e.target.value)}
                        />
                    </div>

                    <div style={{ marginBottom: "10px" }}>
                        <label htmlFor="loanTermYears">
                            Loan Term
                        </label>
                        <br />
                        <select
                            id="loanTermYears"
                            value={loanTermYears}
                            onChange={(e) =>
                                setLoanTermYears(Number(e.target.value))
                            }
                        >
                            <option value={30}>30 Year</option>
                            <option value={15}>15 Year</option>
                        </select>
                    </div>

                </div>

                {/* Right Column */}
                <div>

                    <div style={{ marginBottom: "10px" }}>
                        <label htmlFor="hoaMonthlyFees">
                            HOA Monthly Fees
                        </label>
                        <br />
                        <input
                            type="number"
                            min="0"
                            step="0.01"
                            value={hoaMonthlyFees}
                            onChange={(e) => setHoaMonthlyFees(e.target.value)}
                        />
                    </div>

                    <div style={{ marginBottom: "10px" }}>
                        <label htmlFor="propertyTaxRate">
                            Property Tax Rate (%)
                        </label>
                        <br />
                        <input
                            type="number"
                            min="0"
                            step="0.01"
                            value={propertyTaxRate}
                            onChange={(e) => setPropertyTaxRate(e.target.value)}
                        />
                    </div>

                    <div style={{ marginBottom: "10px" }}>
                        <label htmlFor="homeownersInsuranceRate">
                            Homeowners Insurance Rate (%)
                        </label>
                        <br />
                        <input
                            type="number"
                            min="0"
                            step="0.01"
                            value={homeownersInsuranceRate}
                            onChange={(e) =>
                                setHomeownersInsuranceRate(e.target.value)
                            }
                        />
                    </div>

                    <div style={{ marginBottom: "10px" }}>
                        <label htmlFor="pmiRate">
                            PMI Rate (%)
                        </label>
                        <br />
                        <input
                            type="number"
                            min="0"
                            step="0.01"
                            value={pmiRate}
                            onChange={(e) => setPmiRate(e.target.value)}
                        />
                    </div>

                </div>

            </div>

            <button onClick={calculateAffordability}>
                Calculate
            </button>

            <div style={{ marginTop: "30px" }}>
                <h2>
                    Estimated Max Home Price: $
                    {result.toLocaleString()}
                </h2>
            </div>

        </div>
    );
}

export default App;