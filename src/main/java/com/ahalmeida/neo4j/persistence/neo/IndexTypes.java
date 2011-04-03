package com.ahalmeida.neo4j.persistence.neo;

import com.ahalmeida.neo4j.indexer.IndexType;

public enum IndexTypes implements IndexType {
	PLACES {
		@Override
		public String indexName() {
			return "places";
		}
	},

	PLACES_FULLTEXT {
		@Override
		public String indexName() {
			return "places_fulltext";
		}
	},
	
	PERSONS {
		@Override
		public String indexName() {
			return "persons";
		}
	},
	
	PERSONS_FULLTEXT {
		@Override
		public String indexName() {
			return "persons_fulltext";
		}
	};
}
