import java.util.Scanner;

// test comment
public class paintcalc {
    public static void main(String[] args) {
        System.out.println("Welcome to the Painter Calculator!");
        Scanner scanner = new Scanner(System.in);

        double totalArea = 0;
        double totalCost = 0;

        while (true) {
            System.out.println("Enter the type of shape: Rectangle, Circle, Triangle or Trapezoid ");
            String shapeType = scanner.nextLine().trim();
            double area = 0;

            switch (shapeType.toLowerCase()) {
                case "rectangle":
                    System.out.println("Enter the width (in meters): ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    }
                    double width = scanner.nextDouble();

                    System.out.println("Enter the height (in meters): ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    }
                    double height = scanner.nextDouble();
                    area = width * height;
                    break;

                case "circle":
                    System.out.println("Enter the radius (in meters): ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    }
                    double radius = scanner.nextDouble();
                    area = Math.PI * Math.pow(radius, 2);
                    break;

                case "triangle":
                    System.out.println("Enter the base (in meters): ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    }
                    double base = scanner.nextDouble();

                    System.out.println("Enter the height (in meters): ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    }
                    double triangleHeight = scanner.nextDouble();
                    area = 0.5 * base * triangleHeight;
                    break;

                case "trapezoid":
                    System.out.println("Enter the length of the first base (in meters): ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    }
                    double base1 = scanner.nextDouble();

                    System.out.println("Enter the length of the second base (in meters): ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    }
                    double base2 = scanner.nextDouble();

                    System.out.println("Enter the height (in meters): ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    }
                    double trapezoidHeight = scanner.nextDouble();
                    area = 0.5 * (base1 + base2) * trapezoidHeight;
                    break;

                default:
                    System.out.println("Invalid shape type entered.");
                    continue;
            }
            scanner.nextLine();
            totalArea += area;

            System.out.println("Do you want to enter the cost for the paint? (yes / anything else for no)");
            String answer = scanner.nextLine().trim();
            double cost = 0;

            if (answer.equalsIgnoreCase("yes")) {
                while (true) {
                    System.out.println("Enter the cost per square meter: ");
                    String line = scanner.nextLine().trim();


                    if (!line.isEmpty()) {

                        try {
                            cost = Double.parseDouble(line);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid cost. Please enter a valid number.");

                        }
                    } else {
                        System.out.println("No input provided. Please enter a valid number.");
                    }
                }
            }
            else {
                System.out.println("Select a cost range per square meter:");
                System.out.println("1. £1.50");
                System.out.println("2. £2.50");
                System.out.println("3. £3.50");

                while (true) {
                    String input = scanner.nextLine().trim();

                    switch (input) {
                        case "1":
                            cost = 1.50;
                            System.out.println("You selected £1.50.");
                            break;
                        case "2":
                            cost = 2.50;
                            System.out.println("You selected £2.50.");
                            break;
                        case "3":
                            cost = 3.50;
                            System.out.println("You selected £3.50.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please select 1, 2, or 3.");
                            continue;
                    }
                    break;
                }
            }

            double currentCost = area * cost;
            totalCost += currentCost;

            System.out.println("Amount of paint needed for this shape: " + area + " square meters");
            System.out.println("Cost for this shape: £"+ currentCost);
            System.out.println("Do you wish to calculate for another shape? (yes / anything else for no)");
            if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                break;
            }
        }

        System.out.println("Total amount of paint needed:" + totalArea + " square meters");
        System.out.println("Total cost for all paint £"+ totalCost);
        scanner.close();
    }
}

