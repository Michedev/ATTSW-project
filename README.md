# ATTSW-project
![Build](https://img.shields.io/github/workflow/status/Michedev/ATTSW-project/Maven%20test%20and%20package)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Michedev_ATTSW-project&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Michedev_ATTSW-project)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Michedev_ATTSW-project&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Michedev_ATTSW-project)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Michedev_ATTSW-project&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Michedev_ATTSW-project)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Michedev_ATTSW-project&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=Michedev_ATTSW-project)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Michedev_ATTSW-project&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=Michedev_ATTSW-project)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Michedev_ATTSW-project&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Michedev_ATTSW-project)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Michedev_ATTSW-project&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=Michedev_ATTSW-project)

## Introduction

The application is a _Task Manager_, which allow the user to create, modify and delete its own tasks. 
Each task has a deadline date, which will turn the task card color into red if the task 
is not completed before that date. Further, the app features a login system.

## Application screenshots
![app_screenshot5](https://user-images.githubusercontent.com/12683228/158583453-f75155fb-fc43-471e-8568-d65e7bdf3363.png)
![app_screenshot4](https://user-images.githubusercontent.com/12683228/158583481-8f331194-9731-44e6-bb6b-1d03ce250db6.png)
![app_screenshot3](https://user-images.githubusercontent.com/12683228/158583484-315c1f9a-85d9-4f1c-aef6-1d7fb370d8fd.png)
![app_screenshot2](https://user-images.githubusercontent.com/12683228/158583486-f4cb17ab-fdd3-4178-b91a-327f60c8bc2a.png)
![app screenshot](https://user-images.githubusercontent.com/12683228/158583488-e33d7e8a-67cf-4aac-89ee-eb93f32387e1.png)


## How to run

Before the software execution, in order to allow to display the GUI through docker container prompt in any shell the following command
```bash
xhost +local:root
```
Then to build the jar file prompt
	
```bash
mvn package [-DskipTests]
```
To start the application prompt
	
```bash
[sudo] docker compose up
```

Once the application is stopped, to revert the permission to execute GUI applications through docker container prompt
	
```bash
xhost -local:root
```
	
