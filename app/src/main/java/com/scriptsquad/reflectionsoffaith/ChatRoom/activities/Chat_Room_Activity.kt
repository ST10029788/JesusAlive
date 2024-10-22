package com.scriptsquad.reflectionsoffaith.ChatRoom.activities

import android.Manifest
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.scriptsquad.reflectionsoffaith.ChatRoom.AdapterChatRoom
import com.scriptsquad.reflectionsoffaith.ChatRoom.FirebaseCords.FirebaseCords
import com.scriptsquad.reflectionsoffaith.ChatRoom.model.ChatRoomModel
import com.scriptsquad.reflectionsoffaith.StartPage.Main_Home_Screen
import com.scriptsquad.reflectionsoffaith.R
import com.scriptsquad.reflectionsoffaith.databinding.ActivityChatRoomBinding
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import java.text.SimpleDateFormat
import java.util.Date


class Chat_Room_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var chatRoomAdapter: AdapterChatRoom
    private lateinit var progressDialog: ProgressDialog
    private var message = ""

    private companion object {
        private const val TAG = "CHAT_ROOM_TAG"
        //https://www.reddit.com/r/NewTubers/comments/nm09bz/list_of_500_words_to_put_in_your_banned_words_list/

        private val FORBIDDEN_WORDS = listOf("Arse","Ass","Asshole","Homosexual","Homophobic","Gay","Lgbt","Jew","Jewish","Anti-semitic","Chink","Muslims","Muslim","Isis","Islamophobe","homophobe ","Bombing","Sexyhot","Bastard","Bitch","Fucker","Cunt","Damn","Fuck","Goddamn","Shit","Motherfucker","Nigga","Nigger","Prick","Shit","shit ass","Shitass","son of a bitch","Whore","Thot","Slut","Faggot","Dick","Pussy","Penis","Vagina","Negro","Coon","Bitched","Sexist","Freaking","Cock","Sucker","Lick","Licker","Rape","Molest","Anal","Buttrape","Coont","Cancer","Sex","Retard","Fuckface","Dumbass","5h1t","5hit","A_s_s","a2m","a55","adult","amateur","anal","anal impaler†††","anal leakage†††","anilingus","anus","ar5e","arrse","arse","arsehole","ass","ass fuck†††","asses","assfucker","ass-fucker","assfukka","asshole","asshole","assholes","assmucus†††","assmunch","asswhole","autoerotic","b!tch","b00bs","b17ch","b1tch","ballbag","ballsack","bang (one's) box†††","bangbros","bareback","bastard","beastial","beastiality","beef curtain†††","bellend","bestial","bestiality","bi+ch","biatch","bimbos","birdlock","bitch","bitch tit†††","bitcher","bitchers","bitches","bitchin","bitching","bloody","blow job","blow me†††","blow mud†††","blowjob","blowjobs","blue waffle†††","blumpkin†††","boiolas","bollock","bollok","boner","boob","boobs","booobs","boooobs","booooobs","booooooobs","breasts","buceta","bugger","bum","bunny fucker","bust a load†††","busty","butt","butt fuck†††","butthole","buttmuch","buttplug","c0ck","c0cksucker","carpet muncher","carpetmuncher","cawk","chink","choade†††","chota bags†††","cipa","cl1t","clit","clit licker†††","clitoris","clits","clitty litter†††","clusterfuck","cnut","cock","cock pocket†††","cock snot†††","cockface","cockhead","cockmunch","cockmuncher","cocks","cocksuck ","cocksucked ","cocksucker","cock-sucker","cocksucking","cocksucks ","cocksuka","cocksukka","cok","cokmuncher","coksucka","coon","cop some wood†††","cornhole†††","corp whore†††","cox","cum","cum chugger","cum dumpster","cum freak","cum guzzler†††","cumdump†††","cummer","cumming","cums","cumshot","cunilingus","cunillingus","cunnilingus","cunt","cunt hair†††","cuntbag†††","cuntlick ","cuntlicker ","cuntlicking ","cunts","cuntsicle†††","cunt-struck†††","cut rope†††","cyalis","cyberfuc","cyberfuck ","cyberfucked ","cyberfucker","cyberfuckers","cyberfucking ","d1ck","damn","dick","dick hole†††","dick shy†††","dickhead","dildo","dildos","dink","dinks","dirsa","dirty Sanchez†††","dlck","dog-fucker","doggie style","doggiestyle","doggin","dogging","donkeyribber","doosh","duche","dyke","eat a dick†††","eat hair pie†††","ejaculate","ejaculated","ejaculates ","ejaculating ","ejaculatings","ejaculation","ejakulate","erotic","f u c k","f u c k e r","f_u_c_k","f4nny","facial†††","fag","fagging","faggitt","faggot","faggs","fagot","fagots","fags","fanny","fannyflaps","fannyfucker","fanyy","fatass","fcuk","fcuker","fcuking","feck","fecker","felching","fellate","fellatio","fingerfuck ","fingerfucked ","fingerfucker ","fingerfuckers","fingerfucking ","fingerfucks ","fist fuck†††","fistfuck","fistfucked ","fistfucker ","fistfuckers ","fistfucking ","fistfuckings ","fistfucks ","flange","flog the log†††","fook","fooker","fuck hole†††","fuck puppet†††","fuck trophy†††","fuck yo mama†††","fuck†††","fucka","fuck-ass†††","fuck-bitch†††","fucked","fucker","fuckers","fuckhead","fuckheads","fuckin","fucking","fuckings","fuckingshitmotherfucker","fuckme ","fuckmeat†††","fucks","fucktoy†††","fuckwhit","fuckwit","fudge packer","fudgepacker","fuk","fuker","fukker","fukkin","fuks","fukwhit","fukwit","fux","fux0r","gangbang","gangbang†††","gang-bang†††","gangbanged ","gangbangs ","gassy ass†††","gaylord","gaysex","goatse","god","god damn","god-dam","goddamn","goddamned","god-damned","ham flap†††","hardcoresex ","hell","heshe","hoar","hoare","hoer","homo","homoerotic","hore","horniest","horny","hotsex","how to kill","how to murdep","jackoff","jack-off ","jap","jerk","jerk-off ","jism","jiz ","jizm ","jizz","kawk","kinky Jesus†††","knob","knob end","knobead","knobed","knobend","knobend","knobhead","knobjocky","knobjokey","kock","kondum","kondums","kum","kummer","kumming","kums","kunilingus","kwif†††","l3i+ch","l3itch","labia","LEN","lmao","lmfao","lmfao","lust","lusting","m0f0","m0fo","m45terbate","ma5terb8","ma5terbate","mafugly†††","masochist","masterb8","masterbat*","masterbat3","masterbate","master-bate","masterbation","masterbations","masturbate","mof0","mofo","mo-fo","mothafuck","mothafucka","mothafuckas","mothafuckaz","mothafucked ","mothafucker","mothafuckers","mothafuckin","mothafucking ","mothafuckings","mothafucks","mother fucker","mother fucker†††","motherfuck","motherfucked","motherfucker","motherfuckers","motherfuckin","motherfucking","motherfuckings","motherfuckka","motherfucks","muff","muff puff†††","mutha","muthafecker","muthafuckker","muther","mutherfucker","n1gga","n1gger","nazi","need the dick†††","nigg3r","nigg4h","nigga","niggah","niggas","niggaz","nigger","niggers ","nob","nob jokey","nobhead","nobjocky","nobjokey","numbnuts","nut butter†††","nutsack","omg","orgasim ","orgasims ","orgasm","orgasms ","p0rn","pawn","pecker","penis","penisfucker","phonesex","phuck","phuk","phuked","phuking","phukked","phukking","phuks","phuq","pigfucker","pimpis","piss","pissed","pisser","pissers","pisses ","pissflaps","pissin ","pissing","pissoff ","poop","porn","porno","pornography","pornos","prick","pricks ","pron","pube","pusse","pussi","pussies","pussy","pussy fart†††","pussy palace†††","pussys ","queaf†††","queer","rectum","retard","rimjaw","rimming","s hit","s.o.b.","s_h_i_t","sadism","sadist","sandbar†††","sausage queen†††","schlong","screwing","scroat","scrote","scrotum","semen","sex","sh!+","sh!t","sh1t","shag","shagger","shaggin","shagging","shemale","shi+","shit","shit fucker†††","shitdick","shite","shited","shitey","shitfuck","shitfull","shithead","shiting","shitings","shits","shitted","shitter","shitters ","shitting","shittings","shitty ","skank","slope†††","slut","slut bucket†††","sluts","smegma","smut","snatch","son-of-a-bitch","spac","spunk","t1tt1e5","t1tties","teets","teez","testical","testicle","tit","tit wank†††","titfuck","tits","titt","tittie5","tittiefucker","titties","tittyfuck","tittywank","titwank","tosser","turd","tw4t","twat","twathead","twatty","twunt","twunter","v14gra","v1gra","vagina","viagra","vulva","w00se","wang","wank","wanker","wanky","whoar","whore","willies","willy","wtf","xrated","xxx","sucker","dumbass","Kys","Kill","Die","Cliff","Bridge","Shooting","Shoot","Bomb","Terrorist","Terrorism","Bombed","Trump","Maga","Conservative","Make america great again","Far right","Necrophilia","Mongoloid","Furfag","Cp","Pedo","Pedophile","Pedophilia","Child predator","Predatory","Depression","Cut myself","I want to die","Fuck life","Redtube","Loli","Lolicon") // Add your forbidden words here
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setCanceledOnTouchOutside(false)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showsplash()
        initChatList()
        chatRoomAdapter.startListening()

        binding.sendFab.setOnClickListener {
            addMessage()
        }

        binding.attachFab.setOnClickListener {
            openExplorer()
        }

        binding.imageBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

//method used from YouTube
//https://youtu.be/jgtxeilPLnI?si=wyZ4HSvI9m_9XIyP
//channel: Alex Mamo

    private fun initChatList() {

        val firebaseCords = FirebaseCords()

        binding.chatRoomRv.setHasFixedSize(true)


        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        layoutManager.stackFromEnd = false
        binding.chatRoomRv.layoutManager = layoutManager


        val query: Query =
            firebaseCords.MAIN_CHAT_DATABASE.orderBy("timestamp", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<ChatRoomModel>()
            .setQuery(query, ChatRoomModel::class.java)
            .build()



        chatRoomAdapter = AdapterChatRoom(options, this) {
            scrollToLastItem()
        }
        binding.chatRoomRv.adapter = chatRoomAdapter
        chatRoomAdapter.startListening()

    }
//method used from YouTube
//https://youtu.be/0gLr-pBIPhI?si=EfHlUd8kPy94o4FR
//PedroTech

    private fun addMessage() {
        val date = Date()
        val simpleDataFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        message = binding.chatBox.text.toString()

        // Sanitize and filter the message
        val sanitizedMessage = sanitizeMessage(message)

        if (!TextUtils.isEmpty(sanitizedMessage)) {
            val uid = firebaseAuth.currentUser!!.uid
            val ref = FirebaseDatabase.getInstance().getReference("Users")
            ref.child("$uid").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = "${snapshot.child("name").value}"
                    val profileImageUrl = "${snapshot.child("profileImageURl").value}"

                    val hashMap = HashMap<String, Any>().apply {
                        put("message", sanitizedMessage)
                        put("userName", name)
                        put("timestamp", FieldValue.serverTimestamp())
                        put("messageId", simpleDataFormat.format(date))
                        put("profileImageUrl", profileImageUrl)
                        put("chat_image", "")
                        put("chatTime", System.currentTimeMillis())
                        put("uid", uid)
                    }

                    val fireBaseCords = FirebaseCords()
                    fireBaseCords.MAIN_CHAT_DATABASE.document(simpleDataFormat.format(date)).set(hashMap)
                        .addOnSuccessListener {
                            Toast.makeText(this@Chat_Room_Activity, "Message Sent", Toast.LENGTH_SHORT).show()
                            binding.chatBox.setText("")
                            scrollToLastItem()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this@Chat_Room_Activity, "Failed To Send", Toast.LENGTH_SHORT).show()
                            Log.e(TAG, "addMessage: Failed due to ${e.message}")
                        }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: $error")
                }
            })
        } else {
            // Provide feedback if the message was empty or contained forbidden words
            Toast.makeText(this, "Your message contains inappropriate content.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sanitizeMessage(input: String): String {
        var sanitizedInput = input.trim()
        FORBIDDEN_WORDS.forEach { forbiddenWord ->
            sanitizedInput = sanitizedInput.replace(Regex("(?i)$forbiddenWord"), "***")
        }
        return sanitizedInput
    }


//method used from YouTube
//https://youtu.be/vaKFSUmZ31A?si=-eab9NBtwC4rwwNG
//Programmer World

    private fun openExplorer() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            chooseImage()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 20
                )
            else {
                Toast.makeText(
                    this@Chat_Room_Activity,
                    "storage Permission Needed",
                    Toast.LENGTH_SHORT
                ).show()
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 20
                )
            }


        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 20) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@Chat_Room_Activity, "Permission Granted", Toast.LENGTH_SHORT)
                    .show()
                chooseImage()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // Use the returned uri.
            val uriContent = result.uriContent
            // optional usage

            // Proceed with your logic using the uriContent or uriFilePath
            val intent = Intent(this, Image_Upload_Preview_Activity::class.java)
            intent.putExtra("imageUri", uriContent.toString())
            startActivity(intent)

        } else {
            // Handle the error condition
            val exception = result.error
            Toast.makeText(this, exception?.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun chooseImage() {
        // Launch the CropImage activity with the specified options
        cropImage.launch(
            CropImageContractOptions(null, CropImageOptions().apply {
                guidelines = CropImageView.Guidelines.ON
            })
        )
    }


    private fun showsplash() {
        val dialog =
            Dialog(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.splash_chatroom)
        dialog.setCancelable(true)
        dialog.show()
        val handler = Handler()
        val runnable = Runnable {
            if (firebaseAuth.currentUser != null) {
                dialog.dismiss()
            } else if (firebaseAuth.currentUser == null) {
                startActivity(Intent(this, Main_Home_Screen::class.java))

            }

        }
        handler.postDelayed(runnable, 5000)
    }


    private fun scrollToLastItem() {
        binding.chatRoomRv.postDelayed({
            val itemCount = chatRoomAdapter.itemCount
            if (itemCount > 0) {
                binding.chatRoomRv.scrollToPosition(0)
            }
        }, 100)
    }


}