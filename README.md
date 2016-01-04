# nikodemocracy
Simple voting system inspired by secure elections protocol with 2 central authorities mentioned in Bruce Schneier's Applied Cryptography.

## Description

According to Schenier, ideal voting protocol has, at the very least, these six requirements:
 1. Only authorized voters can vote.
 2. No one can vote more than once.
 3. No one can determine for whom anyone else voted.
 4. No one can change or duplicate anyone else's vote.
 5. Every voter can make sure that his vote has been taken into account in the final tabulation.
 6. (Optional) Everyone knows who voted and who didn't.

One of the proposed ways to achieve this is to use two central authorities.

### Original protocol

#### Registration authority (RA)
responsible for creating election and registering voters.

#### Tabulation authority (TA)

responsible for tabulating votes and publishing results.
1. All voters send RA a request for a voter key.
2. RA sends every voter randomly chosen voter key, and also keeps a list, who got what voter key.
3. RA sends list of all voter keys to TA (without mappings to voters).
4. Every voter randomly chooses a vote key and submits vote to TA that consists of (voter key, vote key, chosen candidate/option).
5. When TA receives a vote, the authority verifies that vote contains a valid and not yet used voter key and stores pair vote key - chosen option.
6. In the final tabulation, TA counts all votes and publishes list of all pairs vote key - chosen option. 

Any voter can verify his vote was correctly counted by identifying it through vote key.

Protocol is vulnerable to collusion between RA and TA. If they cooperated, they could correlate databases and figure out who voted for whom.


### My simplistic implementation
Original protocol assumes that all communication between voters and authorities is asymmetrically (or more likely hybridly) encrypted, which is not feasible for me.

Another change is that when election admin (who acts in a role of RA) creates an election, he must specify all eligible voters who are immediately provided voter keys instead of giving anyone a chance to request a permission to vote (and hence voter key).  

To see more I highly recommend checking out project's use cases: https://github.com/Peter-Nikodem/nikodemocracy/wiki/Use-Cases
