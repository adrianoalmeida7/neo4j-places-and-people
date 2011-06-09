package com.ahalmeida.neo4j.persistence.dao;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

abstract public class Neo4JIntegration {
	private static final String DATABASE_DIR = "/Users/ahalmeida/Projects/java/neo4j-vraptor/graphdb-test";
	private EmbeddedGraphDatabase db;
	private Transaction tx;

	@Before
	public void setup() {
		db = new EmbeddedGraphDatabase(DATABASE_DIR);
		tx = db.beginTx();
	}

	@After
	public void tearDown() {
		tx.success();
		tx.finish();
		db.shutdown();

		deleteDirectory(new File(DATABASE_DIR));
	}

	private boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	protected EmbeddedGraphDatabase getDb() {
		return db;
	}
}
