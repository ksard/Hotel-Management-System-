# Software Development Practice (BlueParadise)

# Introduction
This is the frontend component of a Hotel Management System developed using the Angular framework.
It provides a authentic UI screens using the combination of HTML, CSS and is made dynamic using typescript.

## Prerequisites
Ensure you have the following installed on your machine:
- [Visual Studio Code](https://code.visualstudio.com/download)
- [Node JS](https://nodejs.org/en)
- [Instal Angular 16.0.0] (npm i @angular/cli@16.0.0)

## Getting Started
1. Clone the Repository:
```
git clone https://gitlab.com/cris.kohn.2207/software-development-practice-frontend.git
```
2. Navigate to the Project Directory and open the project in Visual Studio Code.

3. Run the command (npm install) so that all the dependencies are installed in your machine.

4. Then execute the command (ng serve -o) to run the project.

## Project Structure 
The project follows the structure given below:
- `src` : Contains the app component and the index.html page which is the first html page loaded upon serve
- `app` : It is the first component or also we can call it as the root component.
- `Common` : Includes the Header and Footer Components
- `Components` : Includes all the components in the main body
- `Directives` : Includes directives to validate data
- `Modules` : Includes angular material module
- `Services` : Includes *services* to fetch data from APIs, *interceptors* to validate the response errors, *guards* to validate the routing
- `Styles` : Includes common styles to be used across the app 
- `app-routing.module.ts` : Includes all the routing information
- `app.component.html` : Main component html
- `app.component.ts` : Contains logic for the main component
- `app.module.ts` : Registers all the modules and components
- `assets` : Includes the image files
- `environment` : Includes the environment variables and urls
