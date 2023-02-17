package com.aerospike;

/**
 *
 * @author reuven
 */

import java.util.Map;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Info;
import com.aerospike.client.cluster.Node;

public class GetSetNumRecords {
    

/*
 * Copyright 2012-2021 Aerospike, Inc.
 *
 * Portions may be licensed to Aerospike, Inc. under one or more contributor
 * license agreements WHICH ARE COMPATIBLE WITH THE APACHE LICENSE, VERSION 2.0.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
        public static void main(String[] args) {
            AerospikeClient client = new AerospikeClient("44.211.243.226", 3000);
            System.out.println("Connected");
            int masterObjectCount = 0;
            int setObjectCount = 0;
            Node nodes[] = client.getNodes();
            String ns = "test";
            String set = "testset";
            
            // For each node in the cluster
            for (Node node : nodes) {
                System.out.println("node: " + node);
                // Get it's master-objects value and add to the total
                masterObjectCount += GetMasterObjectCount(node, ns);
                setObjectCount += GetSetObjectCount( node, ns, set); 
                
            }
            System.out.println("master objects: " + masterObjectCount);
            System.out.println("set objects: " + setObjectCount);
        }
        private static void GetNamespaceConfig(Node node, String namespace) {
		
		//String filter = "namespace/" + namespace;
                String filter = "namespace/" + namespace;
                // Issue the info request for the namespace
		String tokens = Info.request(null, node, filter);

                int masterObjects = GetTokenMasterObjects(tokens);
                
	}

	private static int GetMasterObjectCount(Node node, String namespace) {
	
                String filter = "namespace/" + namespace;
		String tokens = Info.request(null, node, filter);

                //return the master-objects value
                return(GetTokenMasterObjects(tokens));        
	}
        private static int GetSetObjectCount(Node node, String namespace, String set) {
	
                String filter = "sets/" + namespace + "/" + set;
		String tokens = Info.request(null, node, filter);

                //return the master-objects value
                return(GetSetObjects(tokens));        
	}
        
        private static int GetSetObjects(String tokens) {
		String[] values = tokens.split(":");
                String SET_OBJECT_COUNT_TAG = "objects=";
                int SET_OBJECT_COUNT_TAG_LENGTH = SET_OBJECT_COUNT_TAG.length();
                for (String value : values) {
                    if (value.startsWith(SET_OBJECT_COUNT_TAG)) {
                          return Integer.parseInt(value.substring(SET_OBJECT_COUNT_TAG_LENGTH));
                    }
                }
                return 0;
        }
        
        private static int GetTokenMasterObjects(String tokens) {
		String[] values = tokens.split(";");
                String MASTER_OBJECT_COUNT_TAG = "master_objects=";
                int MASTER_OBJECT_COUNT_TAG_LENGTH = MASTER_OBJECT_COUNT_TAG.length();
                for (String value : values) {
                    if (value.startsWith(MASTER_OBJECT_COUNT_TAG)) {
                        return Integer.parseInt(value.substring(MASTER_OBJECT_COUNT_TAG_LENGTH));
                    }
                }
                return 0;
        }
}
