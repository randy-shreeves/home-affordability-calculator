import { useState } from "react";

function App() {

  const [monthlyPayment, setMonthlyPayment] = useState("");
  const [downPayment, setDownPayment] = useState("");
  const [interestRate, setInterestRate] = useState("");
  const [loanTermYears, setLoanTermYears] = useState<number>(30);
  const [hoaMonthlyFees, setHoaMonthlyFees] = useState("");
  const [propertyTaxRate, setPropertyTaxRate] = useState("");
  const [homeownersInsuranceRate, setHomeownersInsuranceRate] = useState("");
  const [pmiRate, setPmiRate] = useState("");

  const [result, setResult] = useState<number>(0);

  async function calculateAffordability() {
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

  return (
      <div style={{ padding: "2rem", fontFamily: "Arial" }}>

        <h1>Home Affordability Estimator</h1>

          <div style={{ marginBottom: "10px" }}>
              <label htmlFor="monthlyPayment">Monthly Payment</label>
              <br />
              <input
                  type="number"
                  placeholder="Monthly Payment"
                  value={monthlyPayment}
                  onChange={(e) => setMonthlyPayment(e.target.value)}
              />
          </div>

        <div style={{ marginBottom: "10px" }}>
            <label htmlFor="downPayment">Down Payment</label>
            <br />
            <input
              type="number"
              placeholder="Down Payment"
              value={downPayment}
              onChange={(e) => setDownPayment(e.target.value)}
          />
        </div>

          <div style={{ marginBottom: "10px" }}>
              <label htmlFor="interestRate">Interest Rate</label>
              <br />
              <input
                  type="number"
                  placeholder="Interest Rate"
                  value={interestRate}
                  onChange={(e) => setInterestRate(e.target.value)}
              />
          </div>

          <div style={{ marginBottom: "10px" }}>
              <label htmlFor="loanTermYears">Loan Term</label>
              <br />
              <select
                  id="loanTermYears"
                  value={loanTermYears}
                  onChange={(e) => setLoanTermYears(Number(e.target.value))}
              >
                  <option value={30}>30 Year</option>
                  <option value={15}>15 Year</option>
              </select>
          </div>

          <div style={{ marginBottom: "10px" }}>
              <label htmlFor="HOA monthly fees">HOA Monthly Fee</label>
              <br />
              <input
                  type="number"
                  placeholder="HOA Monthly Fee"
                  value={hoaMonthlyFees}
                  onChange={(e) => setHoaMonthlyFees(e.target.value)}
              />
          </div>

          <div style={{ marginBottom: "10px" }}>
              <label htmlFor="propertyTaxRate">Property Tax Rate (%)</label>
              <br />
              <input
                  type="number"
                  placeholder="Property Tax Rate (%)"
                  value={propertyTaxRate}
                  onChange={(e) => setPropertyTaxRate(e.target.value)}
              />
          </div>

          <div style={{ marginBottom: "10px" }}>
              <label htmlFor="homeownersInsuranceRate">Homeowners Insurance Rate (%)</label>
              <br />
              <input
                  type="number"
                  placeholder="Homeowners Insurance Rate(%)"
                  value={homeownersInsuranceRate}
                  onChange={(e) => setHomeownersInsuranceRate(e.target.value)}
              />
          </div>

          <div style={{ marginBottom: "10px" }}>
              <label htmlFor="pmiRate">PMI Rate (%)</label>
              <br />
              <input
                  type="number"
                  placeholder="PMI Rate(%)"
                  value={pmiRate}
                  onChange={(e) => setPmiRate(e.target.value)}
              />
          </div>


          <br />

        <button onClick={calculateAffordability}>
          Calculate
        </button>

          <div style={{ marginTop: "30px" }}>
              <h2>Estimated Max Home Price:</h2>

              <div style={{ fontSize: "24px", fontWeight: "bold" }}>
                  ${result.toLocaleString()}
              </div>
          </div>

      </div>
  );
}

export default App;