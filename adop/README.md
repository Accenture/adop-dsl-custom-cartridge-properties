# ADOP Cartridge DSL Deploy

This cartridge deploys the library to Jenkins additional class path.

## Stucture
A cartridge is broken down into the following sections:

 * infra
  * For infrastructure-related items
 * jenkins
  * For Jenkins-related items
 * src
  * For source control-related items

## Metadata
Each cartridge should contain a "metadata.cartridge" file that specifies the following metadata:

 * `CARTRIDGE_SDK_VERSION`
  * This defines the version of the Cartridge SDK that the cartridge conforms to

## Using this Repository
When developing a cartridge it is advisable to make a copy of this repository and remove all of the README.md files so that it serves as a basis for the new cartridge.
