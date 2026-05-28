import { useState } from "react";

function App() {

  const [monthlyPayment, setMonthlyPayment] = useState("");
  const [downPayment, setDownPayment] = useState("");
  const [interestRate, setInterestRate] = useState("");
  const [loanTermYears, setLoanTermYears] = useState("");
  const [hoaMonthlyFees, setHoaMonthlyFees] = useState("");
  const [propertyTaxRate, setPropertyTaxRate] = useState("");
  const [homeownersInsuranceRate, setHomeownersInsuranceRate] = useState("");
  const [pmiRate, setPmiRate] = useState("");

  const [result, setResult] = useState<number | null>(null);

  async function calculateAffordability() {

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
  }

  return (
      <div style={{ padding: "2rem", fontFamily: "Arial" }}>

        <h1>Home Affordability Estimator</h1>

        <div>
          <input
              type="number"
              placeholder="Monthly Payment"
              value={monthlyPayment}
              onChange={(e) =>
                  setMonthlyPayment(e.target.value)
              }
          />
        </div>

        <div>
          <input
              type="number"
              placeholder="Down Payment"
              value={downPayment}
              onChange={(e) =>
                  setDownPayment(e.target.value)
              }
          />
        </div>

        <div>
          <input
              type="number"
              placeholder="Interest Rate"
              value={interestRate}
              onChange={(e) =>
                  setInterestRate(e.target.value)
              }
          />
        </div>

        <div>
          <input
              type="number"
              placeholder="Loan Term (Years)"
              value={loanTermYears}
              onChange={(e) =>
                  setLoanTermYears(e.target.value)
              }
          />
        </div>

        <div>
          <input
              type="number"
              placeholder="HOA Monthly Fees"
              value={hoaMonthlyFees}
              onChange={(e) =>
                  setHoaMonthlyFees(e.target.value)
              }
          />
        </div>

        <div>
          <input
              type="number"
              placeholder="Property Tax Rate (%)"
              value={propertyTaxRate}
              onChange={(e) =>
                  setPropertyTaxRate(e.target.value)
              }
          />
        </div>

        <div>
          <input
              type="number"
              placeholder="Homeowners Insurance Rate (%)"
              value={homeownersInsuranceRate}
              onChange={(e) =>
                  setHomeownersInsuranceRate(e.target.value)
              }
          />
        </div>

        <div>
          <input
              type="number"
              placeholder="PMI Rate (%)"
              value={pmiRate}
              onChange={(e) =>
                  setPmiRate(e.target.value)
              }
          />
        </div>

        <br />

        <button onClick={calculateAffordability}>
          Calculate
        </button>

        {result !== null && (
            <h2>
              Estimated Max Home Price: $
              {result.toLocaleString()}
            </h2>
        )}

      </div>
  );
}

export default App;