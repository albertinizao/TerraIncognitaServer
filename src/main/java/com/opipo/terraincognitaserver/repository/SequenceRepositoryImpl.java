package com.opipo.terraincognitaserver.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.opipo.terraincognitaserver.dto.SequenceId;

@Repository
public class SequenceRepositoryImpl implements SequenceRepository {

    @Autowired
    private MongoOperations mongoOperation;

    @Override
    public long getNextSequenceId(String key) {
        Query query = new Query(Criteria.where("_id").is(key));

        // increase sequence id by 1
        Update update = new Update();
        update.inc("seq", 1);

        // return new increased id
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        // this is the magic happened.
        SequenceId seqId = mongoOperation.findAndModify(query, update, options, SequenceId.class);

        // if no id, throws SequenceException
        // optional, just a way to tell user when the sequence id is failed to generate.
        if (seqId == null) {
            seqId = new SequenceId();
            seqId.setId(key);
            seqId.setSeq(0L);
            mongoOperation.save(seqId);
        }

        return seqId.getSeq();
    }

}
