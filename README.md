# modules2students Set Up Guide

## Introduction

There are two ways to set up modules2students and run it locally.

The first method is the manual setup which involves downloading all software needed manually while the second method
uses Docker to pull in the necessary images. The following steps have been tested and carried out on a 64-bit Intel Windows PC 
running Windows 11. 

## Manual Installation

### Clone this repo

Please clone this repo to your desired location

### Software Dependencies

Install the following programming languages, runtime and software.

#### Git for Windows v2.42.0.windows.2
1. Download Git for Windows v2.42.0.windows.2 from [here](https://github.com/git-for-windows/git/releases/download/v2.42.0.windows.2/Git-2.42.0.2-64-bit.exe)
2. Execute the installer
3. Check that it is correctly installed by running `git --version` in a shell application like PowerShell

#### Python 3.9.13
1. Download Python 3.9.13 from [here](https://www.python.org/ftp/python/3.9.13/python-3.9.13-amd64.exe)
2. Execute the installer
3. Check that it is correctly installed by running `python --version` in a shell application like PowerShell

#### Java SE Development Kit 19.0.2
1. Download Java SE Development Kit 19 from [here](https://download.oracle.com/java/19/archive/jdk-19.0.2_windows-x64_bin.exe)
2. Execute the installer
3. Check that it is correctly installed by running `java --version` in a shell application like PowerShell

#### Intellij Community Education 2023.2.3
1. Download Intellij Community Education from [here](https://download.jetbrains.com/idea/ideaIC-2023.2.3.exe)
2. Execute the installer
3. Check that it is correctly installed by starting a new Java project
    - Make sure the Java SDK selected is the one installed previously i.e. 19.0.2
    - Compile and make sure it can run successfully

#### Neo4j Desktop 1.5.9
1. Go [here](https://neo4j.com/deployment-center/#desktop) select Windows and download Neo4j Desktop 1.5.9
2. Execute the installer

### Module Data Collection and Preparation

This section contains instructions on how to collect and prepare the data of NTU's modules data.
You can skip this section if you are not planning to collect and prepare the data. The required data has
already been collected and are stored in `modules-recommender/scraping-scripts/scraped-data`

#### Data Collection and Preparation Steps

1. Change directory to `modules-recommender/scraping-scripts/` and create a Python virtual environment
using the command `python -m venv env`
2. Activate the python virtual environment using the command `env/Scripts/Activate.ps1`
3. Install the necessary packages using `pip install -r requirements.txt`
4. Start up jupyter notebook using `jupyter notebook`
5. Run all scripts in `Scraping_NTU_Content_Of_Courses.ipynb` (this will likely take 4-5 hours minimum)
6. Run all scripts in `Merging_Data.ipynb`
7. Run all scripts in `Encoding_Information.ipynb` (this will likely take around 15 minutes to complete)
8. The final files that we require are `all_modules_with_encodings.csv`, `prerequisite_groups.csv`, `mutually_exclusive.csv`.
The rest of the intermediary files can be discarded.
