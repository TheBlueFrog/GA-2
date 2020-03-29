

# Covid Clicker

Goal is to simulate behavior of large clusters of Covid Clickers

A clicker is a self-contained RF(?) device that detects the broadcasts
of other clickers within a short radius.  This models the social distancing rules
of COVID-19.

A clicker will click/vibrate otherwise notify the user when another clicker
is within 2 meters.  Possibly with some indication of actual distance, e.g. click
rate or amplitude.

Each clicker has a unique identifier that it broadcasts periodically.  Initial
expection is that is on the order on once per second.  It may
be that the rate of broadcast is variable e.g. being dependent on how many others are
around.  

When a clicker A receives clicker's B broadcast the identifier of B is
compared to a 'silent' database and if <b>not</b> found issues a notification.
If B's identifier is in the database no notification is issued.  This
allows users to establish a set of clickers whose proximity is ignored.

Controls:
- **silence** - when clicker B is within range and notifications are being posted
        this will enter B's identification in the database and result in
        B not producing notification when in proximity.
- **on/off** - optional, depends on power/battery tradeoffs. if we can get the
        lifetime long enough it is not needed.
- **unsilence** - nice-to-have but not essential if clickers are        
        cheap enough to simply replace one or both if notifications are again desired.
        
Possibly a user may have multiple clickers, one for at-home, one for at-work
and another for in-public use.  Depends on pricing.
    


