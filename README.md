# Key-Value DataStorage

A file-based key-value data store that supports the basic CRD (create, read, and delete)
operations. This data store is meant to be used as a local storage for one single process on one
laptop.

## Getting Started

1. It can be initialized using an optional file path. If one is not provided, it will reliably
create itself in a reasonable location on the laptop.
2. A new key-value pair can be added to the data store using the Create operation. The key
is always a string - capped at 32chars. The value is always a JSON object - capped at
16KB.
3. Read operation can be performed by providing a key and it will give a proper value if a key is present.
4. Delete operation can be performed by providing a key.

### Prerequisites

To reduce the size of the jar dependencies are not bundled along with it, add the following dependencies for 
the smooth running of the jar.

      <dependencies>
            <dependency>
                <groupId>com.google.code.gson</groupId>
                <artifactId>gson</artifactId>
                <version>2.8.6</version>
            </dependency>

            <dependency>
                <groupId>com.fasterxml.jackson.core</groupId>
                <artifactId>jackson-databind</artifactId>
                <version>2.6.3</version>
            </dependency>

            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-io</artifactId>
                <version>1.3.2</version>
            </dependency>

            <dependency>
                <groupId>org.json</groupId>
                <artifactId>json</artifactId>
                <version>20190722</version>
            </dependency>

            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-api</artifactId>
                <version>5.6.0</version>
                <scope>test</scope>
            </dependency>

            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.4</version>
                <scope>compile</scope>
            </dependency>


        </dependencies>
        
  
