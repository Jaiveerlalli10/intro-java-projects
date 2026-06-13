# Election Simulator

A Java console app that simulates a two-party election across multiple 
districts and runs repeated simulations to model voting outcomes.

## How It Works
- Runs 5 simulations across 10 districts
- Each district generates a random voter turnout and applies polling 
  averages with Gaussian error to calculate votes
- Outputs per-district results, total turnout, vote percentages, and 
  a visual emoji bar chart for each simulation
- Declares a winner based on average results across all simulations

## Example Output
District #1 - 🟪 412  🟡 389
District #2 - 🟪 278  🟡 301
...
🟪 Win = true (51.24%)
🟡 Win = false (48.76%)

## How to Run
1. Compile: `javac ElectionSimulator.java`
2. Run: `java ElectionSimulator`

## Concepts Used
- Nested loops and simulation logic
- Random number generation with Gaussian distribution
- Math rounding and percentage calculations
- Console-based data visualization
